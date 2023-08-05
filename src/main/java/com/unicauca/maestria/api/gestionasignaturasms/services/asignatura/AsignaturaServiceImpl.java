package com.unicauca.maestria.api.gestionasignaturasms.services.asignatura;

import com.unicauca.maestria.api.gestionasignaturasms.domain.Asignatura;
import com.unicauca.maestria.api.gestionasignaturasms.domain.DocenteAsignatura;
import com.unicauca.maestria.api.gestionasignaturasms.dtos.AsignaturaCrearDto;
import com.unicauca.maestria.api.gestionasignaturasms.dtos.AsignaturaListarDto;
import com.unicauca.maestria.api.gestionasignaturasms.dtos.CamposUnicosDto;
import com.unicauca.maestria.api.gestionasignaturasms.exceptions.FieldErrorException;
import com.unicauca.maestria.api.gestionasignaturasms.exceptions.FieldUniqueException;
import com.unicauca.maestria.api.gestionasignaturasms.exceptions.ResourceNotFoundException;
import com.unicauca.maestria.api.gestionasignaturasms.mappers.AsignaturaCrearMapper;
import com.unicauca.maestria.api.gestionasignaturasms.mappers.AsignaturaListarMapper;
import com.unicauca.maestria.api.gestionasignaturasms.repositories.AsignaturaRepository;
import com.unicauca.maestria.api.gestionasignaturasms.repositories.DocenteAsignaturaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AsignaturaServiceImpl implements AsignaturaService {

    private final AsignaturaRepository asigRepository;
    private final DocenteAsignaturaRepository docenteAsigRepository;
    private final AsignaturaCrearMapper asignaturaCrearMapper;
    private final AsignaturaListarMapper asignaturaListarMapper;
    private final InformacionUnicaAsignatura informacionUnicaAsignatura;

    @Override
    public AsignaturaListarDto crear(AsignaturaCrearDto asignatura, BindingResult result) {
        if (result.hasErrors()) {
            throw new FieldErrorException(result);
        }
        CamposUnicosDto camposUnicos = informacionUnicaAsignatura.apply(asignatura);

        Map<String, String> validacionCamposUnicos = validacionCampoUnicos(camposUnicos, null);
        if (!validacionCamposUnicos.isEmpty()) {
            throw new FieldUniqueException(validacionCamposUnicos);
        }

        Asignatura asignaturaDb = asigRepository.save(asignaturaCrearMapper.toEntity(asignatura));

        return asignaturaListarMapper.toDto(asignaturaDb);
    }

    @Override
    public AsignaturaListarDto editar(Long id, AsignaturaCrearDto asignatura, BindingResult result) {
        if(result.hasErrors()) {
            throw new FieldErrorException(result);
        }

        Asignatura asignaturaTmp = asignaturaCrearMapper.toEntity(asignatura);
        Asignatura asignaturaBD = asigRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Asignatura con id: "+id+" No encontrada"));

        CamposUnicosDto camposUnicos = informacionUnicaAsignatura.apply(asignatura);
        CamposUnicosDto camposUnicosBD = informacionUnicaAsignatura.apply(asignaturaCrearMapper.toDto(asignaturaBD));

        Map<String, String> validacionCamposUnicos=validacionCampoUnicos(camposUnicos,camposUnicosBD);
        if(!validacionCamposUnicos.isEmpty()) {
            throw new FieldUniqueException(validacionCamposUnicos);
        }


        actualizarInformacionAsignatura(asignaturaTmp, asignaturaBD);
        Asignatura save = asigRepository.save(asignaturaBD);
        return asignaturaListarMapper.toDto(save);
    }

    public void actualizarInformacionAsignatura(Asignatura asignatura,Asignatura asignaturaBD) {
        asignaturaBD.setContenidoAsignatura(asignatura.getContenidoAsignatura());
        asignaturaBD.setCodigoAsignatura(asignatura.getCodigoAsignatura());
        asignaturaBD.setEstadoAsignatura(asignatura.getEstadoAsignatura());
        asignaturaBD.setNombreAsignatura(asignatura.getNombreAsignatura());
        asignaturaBD.setTipoAsignatura(asignatura.getTipoAsignatura());
        asignaturaBD.setCreditos(asignatura.getCreditos());
        asignaturaBD.setAreaFormacion(asignatura.getAreaFormacion());
        asignaturaBD.setLineaInvestigacionAsignatura(asignatura.getLineaInvestigacionAsignatura());
        asignaturaBD.setFechaAprobacion(asignatura.getFechaAprobacion());
        asignaturaBD.setContenidoProgramatico(asignatura.getContenidoProgramatico());
        asignaturaBD.setHorasNoPresencial(asignatura.getHorasNoPresencial());
        asignaturaBD.setHorasPresencial(asignatura.getHorasPresencial());
        asignaturaBD.setHorasTotal(asignatura.getHorasTotal());
        asignaturaBD.setMicrocurriculo(asignatura.getMicrocurriculo());
        asignaturaBD.setOficioFacultad(asignatura.getOficioFacultad());
        asignaturaBD.setObjetivoAsignatura(asignatura.getObjetivoAsignatura());
    }


    @Override
    @Transactional(readOnly = true)
    public AsignaturaListarDto buscarPorId(Long id) {
        return this.asigRepository.findById(id).map(asignaturaListarMapper::toDto).orElseThrow(() -> new ResourceNotFoundException("Asignatura con id: " + id + " No encontrada"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AsignaturaListarDto> buscarTodo() {
        return asignaturaListarMapper.toDtoList(this.asigRepository.findAll());
    }

    private Map<String, String> validacionCampoUnicos(CamposUnicosDto camposUnicos, CamposUnicosDto camposUnicosBD) {

        Map<String, Function<CamposUnicosDto, Boolean>> map = new HashMap<>();

        map.put("nombre", dto -> (camposUnicosBD == null || !dto.getNombreAsignatura().equals(camposUnicosBD.getNombreAsignatura())) && asigRepository.existsByNombreAsignatura(dto.getNombreAsignatura()));
        map.put("codigo", dto -> (camposUnicosBD == null || !dto.getCodigoAsignatura().equals(camposUnicosBD.getCodigoAsignatura())) && asigRepository.existsByCodigoAsignatura(dto.getCodigoAsignatura()));

        Predicate<Field> existeCampo = campo -> map.containsKey(campo.getName());
        Predicate<Field> existeCampoBD = campoBD -> map.get(campoBD.getName()).apply(camposUnicos);

        return Arrays.stream(camposUnicos.getClass().getDeclaredFields()).filter(existeCampo.and(existeCampoBD))
                .peek(field -> field.setAccessible(true))
                .collect(Collectors.toMap(Field::getName, field -> {
                            Object valorCampo = null;
                            try {
                                field.get(camposUnicos);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                            return mensajeException(field.getName(), valorCampo);
                        }
                ));

    }

    private <T> String mensajeException(String nombreCampo, T valorCampo) {
        return "Campo único, ya existe un docente con la información: " + nombreCampo + ": " + valorCampo;
    }
}
