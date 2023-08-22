package com.unicauca.maestria.api.gestionasignaturasms.services.asignatura;

import com.unicauca.maestria.api.gestionasignaturasms.common.client.ArchivoClient;
import com.unicauca.maestria.api.gestionasignaturasms.domain.ActaAsignatura;
import com.unicauca.maestria.api.gestionasignaturasms.domain.Asignatura;
import com.unicauca.maestria.api.gestionasignaturasms.domain.DocenteAsignatura;
import com.unicauca.maestria.api.gestionasignaturasms.domain.archivos.Acta;
import com.unicauca.maestria.api.gestionasignaturasms.domain.msestudiantedocente.Docente;
import com.unicauca.maestria.api.gestionasignaturasms.dtos.AsignaturaCrearDto;
import com.unicauca.maestria.api.gestionasignaturasms.dtos.AsignaturaListarDto;
import com.unicauca.maestria.api.gestionasignaturasms.dtos.CamposUnicosDto;
import com.unicauca.maestria.api.gestionasignaturasms.dtos.archivos.OficioListarDto;
import com.unicauca.maestria.api.gestionasignaturasms.dtos.archivos.OtroDocListarDto;
import com.unicauca.maestria.api.gestionasignaturasms.exceptions.FieldErrorException;
import com.unicauca.maestria.api.gestionasignaturasms.exceptions.FieldUniqueException;
import com.unicauca.maestria.api.gestionasignaturasms.exceptions.ResourceNotFoundException;
import com.unicauca.maestria.api.gestionasignaturasms.mappers.AsignaturaCrearMapper;
import com.unicauca.maestria.api.gestionasignaturasms.mappers.AsignaturaListarMapper;
import com.unicauca.maestria.api.gestionasignaturasms.mappers.archivos.OficioListarMapper;
import com.unicauca.maestria.api.gestionasignaturasms.mappers.archivos.OtroDocListarMapper;
import com.unicauca.maestria.api.gestionasignaturasms.repositories.AsignaturaRepository;
import com.unicauca.maestria.api.gestionasignaturasms.repositories.DocenteAsignaturaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.lang.reflect.Field;
import java.util.*;
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
    private final OficioListarMapper oficioListarMapper;
    private final OtroDocListarMapper otroDocListarMapper;
    private final ArchivoClient archivoClient;

    @Override
    public AsignaturaListarDto crear(AsignaturaCrearDto asignatura, BindingResult result) {
        if (result.hasErrors()) {
            throw new FieldErrorException(result);
        }
        CamposUnicosDto camposUnicos = informacionUnicaAsignatura.apply(asignatura);

        Map<String, String> validacionCamposUnicos = validacionCampoUnicos(camposUnicos, null);
        System.out.println(validacionCamposUnicos);
        if (!validacionCamposUnicos.isEmpty()) {
            throw new FieldUniqueException(validacionCamposUnicos);
        }

        OficioListarDto oficioTmp = archivoClient.crearOficio(asignatura.getOficioFacultad());
        asignatura.setOficioFacultad(null);
        OtroDocListarDto contenidoTmp = archivoClient.crearOtroDoc(asignatura.getContenidoProgramatico());
        asignatura.setContenidoProgramatico(null);
        OtroDocListarDto microcurTmp = archivoClient.crearOtroDoc(asignatura.getMicrocurriculo());
        asignatura.setMicrocurriculo(null);
        Asignatura asignaturaDb = asigRepository.save(asignaturaCrearMapper.toEntity(asignatura));
        asignaturaDb.setOficioFacultad(oficioListarMapper.toEntity(oficioTmp));
        asignaturaDb.setContenidoProgramatico(otroDocListarMapper.toEntity(contenidoTmp));
        asignaturaDb.setMicrocurriculo(otroDocListarMapper.toEntity(microcurTmp));

        List<ActaAsignatura> actaAsignaturaList = new ArrayList<>();
        ActaAsignatura actaAsignaturaTmp = null;
        for (Acta actaTmp : asignatura.getListaActas()) {
            actaAsignaturaTmp = new ActaAsignatura();
            actaAsignaturaTmp.setActa(actaTmp);
            actaAsignaturaTmp.setAsignatura(asignaturaDb);
            actaAsignaturaList.add(actaAsignaturaTmp);
        }

        List<DocenteAsignatura> docenteAsignaturaList = new ArrayList<>();
        DocenteAsignatura docenteAsignatura = null;
        for (Docente docenteTmp : asignatura.getListaDocentes()) {
            docenteAsignatura = new DocenteAsignatura();
            docenteAsignatura.setDictaAsignatura(true);
            docenteAsignatura.setDocente(docenteTmp);
            docenteAsignatura.setAsignatura(asignaturaDb);
            docenteAsignaturaList.add(docenteAsignatura);
        }

        asignaturaDb.setDocentesAsignaturas(docenteAsignaturaList);
        asignaturaDb.setActasAsignaturas(actaAsignaturaList);
        Asignatura asignaturaSave = asigRepository.save(asignaturaDb);
        return asignaturaListarMapper.toDto(asignaturaSave);
    }

    @Override
    public AsignaturaCrearDto buscarPorIdCompleto(Long id){
        return asigRepository.findById(id).map(asignaturaCrearMapper::toDto).orElseThrow(() -> new ResourceNotFoundException("Asignatura con id: " + id + " No encontrada"));
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
        asignaturaBD.setCodigoAsignatura(asignatura.getCodigoAsignatura());
        asignaturaBD.setNombreAsignatura(asignatura.getNombreAsignatura());
        asignaturaBD.setEstadoAsignatura(asignatura.getEstadoAsignatura());
        asignaturaBD.setFechaAprobacion(asignatura.getFechaAprobacion());
        asignaturaBD.setOficioFacultad(asignatura.getOficioFacultad());
        asignaturaBD.setAreaFormacion(asignatura.getAreaFormacion());
        asignaturaBD.setTipoAsignatura(asignatura.getTipoAsignatura());
        asignaturaBD.setLineaInvestigacionAsignatura(asignatura.getLineaInvestigacionAsignatura());
        asignaturaBD.setCreditos(asignatura.getCreditos());
        asignaturaBD.setObjetivoAsignatura(asignatura.getObjetivoAsignatura());
        asignaturaBD.setContenidoAsignatura(asignatura.getContenidoAsignatura());
        asignaturaBD.setContenidoProgramatico(asignatura.getContenidoProgramatico());
        asignaturaBD.setMicrocurriculo(asignatura.getMicrocurriculo());
        asignaturaBD.setHorasNoPresencial(asignatura.getHorasNoPresencial());
        asignaturaBD.setHorasPresencial(asignatura.getHorasPresencial());
        asignaturaBD.setHorasTotal(asignatura.getHorasTotal());
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

        map.put("nombreAsignatura", dto -> (camposUnicosBD == null || !dto.getNombreAsignatura().equals(camposUnicosBD.getNombreAsignatura())) && asigRepository.existsByNombreAsignatura(dto.getNombreAsignatura()));
        map.put("codigoAsignatura", dto -> (camposUnicosBD == null || !dto.getCodigoAsignatura().equals(camposUnicosBD.getCodigoAsignatura())) && asigRepository.existsByCodigoAsignatura(dto.getCodigoAsignatura()));

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
        return "Campo único, ya existe una Asignaaturaa con la información: " + nombreCampo + ": " + valorCampo;
    }
}
