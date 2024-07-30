package com.tecgesco.wmscerrado;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class App {

	private static final boolean debug = true;
	private static final String APPNOME = "WMSCERRADO";
	private static final String VERSAO = "1.2.2";
	static ConfigDao configDao = new ConfigDao();
	static int porta;
	static TrayIcon trayIcon;

	public static final String getVersao() {
		return VERSAO;
	}

	public static final String getNome() {
		return APPNOME;
	}

	public static final boolean isDebug() {
		return debug;
	}

	public static void main(String[] args) throws AWTException {

		System.out.println("Iniciando a aplicação!");
		System.out.println("Verão: " + getVersao());

		System.out.println("Levantando servidor...");
		try {

			try {

				porta = Integer.parseInt(configDao.lerConfig("porta"));
				ServidorHTTP.iniciarServidor(porta);

			} catch (NumberFormatException e) {

				porta = 0;
				JOptionPane.showMessageDialog(null, "Porta não foi definida no arquivo de configurações.");
				System.out.println("Porta não foi definida no arquivo de configurações.");
				System.exit(0);
			}

			if (SystemTray.isSupported()) {
				exibirTrayIcon();
			} else {
				JOptionPane.showMessageDialog(null, "SystemTray não é suportado neste ambiente.");
				System.out.println("SystemTray não é suportado neste ambiente.");
			}

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Não foi possível iniciar servidor.");
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
	}

	private static void exibirTrayIcon() throws AWTException {

		SystemTray tray = SystemTray.getSystemTray();
		ImageIcon icon = new ImageIcon(App.class.getResource("icon.png"));
		PopupMenu popup = new PopupMenu();
		MenuItem exitItem = new MenuItem("Encerrar Servidor");

		exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		popup.add(exitItem);

		trayIcon = new TrayIcon(icon.getImage(), "WMS API : Rodando", popup);
		trayIcon.displayMessage("WMS API", "Servidor iniciado porta " + porta, MessageType.INFO);
		Toolkit.getDefaultToolkit().beep();
		trayIcon.setImageAutoSize(true);
		tray.add(trayIcon);
	}
}
