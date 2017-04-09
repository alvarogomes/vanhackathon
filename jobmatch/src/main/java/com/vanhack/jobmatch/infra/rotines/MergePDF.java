package com.vanhack.jobmatch.infra.rotines;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;

import com.lowagie.text.pdf.PdfCopyFields;
import com.lowagie.text.pdf.PdfReader;

public class MergePDF {

	public static ByteArrayOutputStream concatenarPDFs(
			ArrayList<ByteArrayOutputStream> listaPdfs) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			PdfCopyFields copy = new PdfCopyFields(outputStream);

			for (ByteArrayOutputStream baos : listaPdfs) {
				copy.addDocument(new PdfReader(baos.toByteArray()));
			}
			copy.close();

			outputStream.flush();
			outputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (outputStream != null)
					outputStream.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		return outputStream;
	}
	
	public static ByteArrayOutputStream concatenarArquivosPDFs(
			ArrayList<InputStream> listaPdfs) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			PdfCopyFields copy = new PdfCopyFields(outputStream);

			for (InputStream baos : listaPdfs) {
				copy.addDocument(
							new PdfReader(IOUtils.toByteArray(baos))
						);
			}
			copy.close();

			outputStream.flush();
			outputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (outputStream != null)
					outputStream.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		return outputStream;
	}
	
}