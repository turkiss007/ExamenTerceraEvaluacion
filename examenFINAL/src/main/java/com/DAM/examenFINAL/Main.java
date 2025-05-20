package com.DAM.examenFINAL;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// LEER FICHERO.TXT
		ArrayList<Evento> listaEventos = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/fichero.txt"))) {

			String linea;

			while ((linea = br.readLine()) != null) {

				String[] partes = linea.split(",", 4);

				if (partes.length == 4) {

					String nombre = partes[0];
					LocalDateTime fecha = LocalDateTime.parse(partes[1]);
					String ubicacion = partes[2];
					String descripcion = partes[3];

					listaEventos.add(new Evento(nombre, fecha, ubicacion, descripcion));
				}
			}

			System.out.println("Archivo 'eventos.txt' leído correctamente");

		} catch (IOException e) {
			System.out.println("Error leyendo el archivo");
			e.printStackTrace();
		}

		Evento evento1 = new Evento();
		evento1.setNombre("Concierto Twenty One Pilots");
		evento1.setFecha(LocalDateTime.of(2025, 4, 21, 21, 00));
		evento1.setUbicacion("Madrid");
		evento1.setDescripcion("Clancy Tour");

		listaEventos.add(evento1);

		// GENERAR FICHERO: SALIDA_EVENTOS.TXT
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/salida_eventos.txt"))) {

			for (Evento e : listaEventos) {
				bw.write(e.getNombre() + "," + e.getFecha() + "," + e.getUbicacion() + "," + e.getDescripcion());
				bw.newLine();
			}

			bw.close();
			System.out.println("Archivo 'salida_eventos.txt' generado correctamente");

		} catch (IOException e) {
			System.out.println("Error generando el archivo");
			e.printStackTrace();
		}

		// GENERAR EXCEL: EVENTOS.XLSX

		XSSFWorkbook libro = new XSSFWorkbook();

		XSSFSheet hoja = libro.createSheet();

		XSSFRow fila0 = hoja.createRow(0);
		XSSFCell celda0 = fila0.createCell(0);
		XSSFCell celda1 = fila0.createCell(1);
		XSSFCell celda2 = fila0.createCell(2);
		XSSFCell celda3 = fila0.createCell(3);

		XSSFFont fuente = libro.createFont();
		fuente.setBold(true);

		XSSFCellStyle cabecera = libro.createCellStyle();
		cabecera.setBorderTop(BorderStyle.THICK);
		cabecera.setBorderBottom(BorderStyle.THICK);
		cabecera.setBorderLeft(BorderStyle.THICK);
		cabecera.setBorderRight(BorderStyle.THICK);
		cabecera.setFont(fuente);

		XSSFCellStyle celdas = libro.createCellStyle();
		celdas.setBorderTop(BorderStyle.THIN);
		celdas.setBorderBottom(BorderStyle.THIN);
		celdas.setBorderLeft(BorderStyle.THIN);
		celdas.setBorderRight(BorderStyle.THIN);

		celda0.setCellValue("NOMBRE");
		celda1.setCellValue("FECHA");
		celda2.setCellValue("UBICACIÓN");
		celda3.setCellValue("DESCRIPCIÓN");

		celda0.setCellStyle(cabecera);
		celda1.setCellStyle(cabecera);
		celda2.setCellStyle(cabecera);
		celda3.setCellStyle(cabecera);

		int i = 1;

		for (Evento e : listaEventos) {
			XSSFRow fila = hoja.createRow(i++);

			XSSFCell celdas0 = fila.createCell(0);

			celdas0.setCellValue(e.getNombre());
			celdas0.setCellStyle(celdas);

			XSSFCell celdas1 = fila.createCell(1);

			celdas1.setCellValue(e.getFecha().toString());
			celdas1.setCellStyle(celdas);

			XSSFCell celdas2 = fila.createCell(2);

			celdas2.setCellValue(e.getUbicacion());
			celdas2.setCellStyle(celdas);

			XSSFCell celdas3 = fila.createCell(3);

			celdas3.setCellValue(e.getDescripcion());
			celdas3.setCellStyle(celdas);
		}

		hoja.autoSizeColumn(0);
		hoja.autoSizeColumn(1);
		hoja.autoSizeColumn(2);
		hoja.autoSizeColumn(3);

		try (FileOutputStream fileOut = new FileOutputStream("src/main/resources/eventos.xlsx")) {

			libro.write(fileOut);
			libro.close();
			System.out.println("Excel 'eventos.xlsx' generado correctamente");

		} catch (IOException e) {
			System.out.println("Error generando el excel");
			e.printStackTrace();
		}

		// GENERAR PDF
		try {
			String destino = "src/main/resources/resumen_eventos.pdf";

			PdfWriter escribir = new PdfWriter(destino);
			PdfDocument pdf = new PdfDocument(escribir);
			Document documento = new Document(pdf);

			Paragraph titulo = new Paragraph("Resumen de Eventos").setFontSize(20).setBold()
					.setFontColor(ColorConstants.RED).setTextAlignment(TextAlignment.CENTER).setMarginBottom(20);
			documento.add(titulo);

			for (Evento e : listaEventos) {
				String eventoTexto = "Nombre: " + e.getNombre() + "\n" + "Fecha: " + e.getFecha().toString() + "\n"
						+ "Ubicación: " + e.getUbicacion() + "\n" + "Descripción: " + e.getDescripcion() + "\n\n";

				Paragraph parrafoEvento = new Paragraph(eventoTexto).setFontSize(12).setMarginBottom(10);
				documento.add(parrafoEvento);
			}

			System.out.println("PDF 'resumen_eventos.pdf' generado correctamente");

			documento.close();
		} catch (IOException e) {
			System.out.println("Error generando el pdf");
			e.printStackTrace();
		}

	}

}
