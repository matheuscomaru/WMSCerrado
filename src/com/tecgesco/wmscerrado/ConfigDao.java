package com.tecgesco.wmscerrado;

import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class ConfigDao {

	public void salvarConfig(String param, String value) {

		try {

			File configFile = new File("config.properties");

			if (!configFile.exists()) {
				configFile.createNewFile();
			}

			FileInputStream in = new FileInputStream(configFile);
			Properties props = new Properties();
			props.load(in);
			in.close();

			FileOutputStream out = new FileOutputStream("config.properties");
			props.setProperty(param, value);
			props.store(out, null);
			out.close();

		} catch (IOException io) {
			io.printStackTrace();
		}

	}

	public String lerConfig(String param) {

		File configFile = new File("config.properties");

		if (!configFile.exists()) {
			try {
				configFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try (InputStream input = new FileInputStream(configFile)) {

			Properties prop = new Properties();
			prop.load(input);
			if (prop.containsKey(param)) {
				return prop.getProperty(param);
			} else {
				System.out.println("A propriedade " + param + " não foi encontrada.");
			}

		} catch (IOException ex) {
			ex.printStackTrace();

		}
		return "";
	}

}