package com.Glebson.FolhadeRosto.service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.Glebson.FolhadeRosto.dto.AllergyDto;
import com.Glebson.FolhadeRosto.dto.CitizenDto;
import com.Glebson.FolhadeRosto.dto.ConditionDto;
import com.Glebson.FolhadeRosto.dto.DrugDto;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class PdfService {

    public byte[] generateFolhaDeRostoPdf(CitizenDto citizenDto, List<ConditionDto> conditions, List<DrugDto> drugs, List<AllergyDto> allergies) throws JRException {                 
        InputStream stream = getClass().getResourceAsStream("/jasperReports/folhaDeRosto.jrxml");         
        JasperReport report = JasperCompileManager.compileReport(stream);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(List.of(citizenDto));
        HashMap<String, Object> params = new HashMap<>();
        JasperPrint print = JasperFillManager.fillReport(report, params, dataSource);
        return JasperExportManager.exportReportToPdf(print);
    }
}