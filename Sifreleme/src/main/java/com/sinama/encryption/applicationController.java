package com.sinama.encryption;


import com.sinama.encryption.business.Constrain;

import java.awt.EventQueue;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class applicationController {
    @FXML
    private TextField sifrelenecekMetin;

    @FXML
    private TextArea sifresizKutu;

    @FXML
    private TextArea sifreliKutu;

    @FXML
    private Button sifreleGonder;

    @FXML
    private TextField anahtar;

    @FXML
    private Label durum;

    @FXML
    private CheckBox spnSifrele;

    @FXML
    private CheckBox shaSifrele;

    @FXML
    private CheckBox dosyaSecim;

    @FXML
    private CheckBox metinSecim;

    @FXML
    private Button dosyaYukle;

    @FXML
    private Label yuklenecekDosya;

    Constrain business = new Constrain();

    public static Socket socket;
    private static DataInputStream din;
    private static DataOutputStream dout;

    private void baglan() {
        try {
            socket = new Socket("127.0.0.1", 5966);
            System.out.println("client baglandı");
            din = new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());
            String msgin = "";
            while (!msgin.equals("exit")) {
                msgin = din.readUTF();
                sifreliKutu.setText(msgin);

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void spnDegis() {
        if (spnSifrele.isSelected()) {
            shaSifrele.setSelected(false);
        }
    }

    public void shaDegis() {
        if (shaSifrele.isSelected()) {
            spnSifrele.setSelected(false);
        }
    }

    public void metinDegis(){
        if(metinSecim.isSelected()){
            dosyaSecim.setSelected(false);
            sifrelenecekMetin.setDisable(false);
            dosyaYukle.setDisable(true);
        }
    }

    public void dosyaDegis(){
        if(dosyaSecim.isSelected()){
            metinSecim.setSelected(false);
            sifrelenecekMetin.setDisable(true);
            dosyaYukle.setDisable(false);
        }
    }

    public void sifreleGonder() throws Exception {

        if (shaSifrele.isSelected()) {
            try {
                String password = business.shaEncrypt(sifrelenecekMetin.getText(), anahtar.getText());
                sifresizKutu.setText(sifrelenecekMetin.getText());
                sifreliKutu.setText(password);
                durum.setText("Şifreleme Başarılı!");
            } catch (Exception e) {
                durum.setText("İşlem Başarısız!");
            }
        } else if (spnSifrele.isSelected()) {
            try {
                String sifre = business.spnEncrypt(sifrelenecekMetin.getText(), anahtar.getText());
                sifresizKutu.setText(sifrelenecekMetin.getText());
                sifreliKutu.setText(sifre);
                durum.setText("Şifreleme Başarılı!");
                String mesaj = "";
            } catch (Exception e) {
                durum.setText("İşlem Başarısız!");
            }
        }
        try {
            dout.writeUTF(sifreliKutu.getText());
        } catch (Exception e) {
            System.out.println(e);
        }


    }

    public void baglantiKur() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                baglan();
            }
        });
    }

    public void sifreCoz() throws Exception {
        if (spnSifrele.isSelected()) {
            String metin = business.spnDecrypt(sifreliKutu.getText(), anahtar.getText());
            sifresizKutu.setText(metin);
        }
    }

    public void dosyaSec() throws Exception {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        File file = chooser.showOpenDialog(new Stage());
        yuklenecekDosya.setText(file.getName());
        if(spnSifrele.isSelected()){
            business.spnFileEncrypt(anahtar.getText(), file);
            sifresizKutu.setText(file.getName());
            sifreliKutu.setText(file.getName() + ".enc");
            durum.setText("Dosya şifreleme başarılı!");
        }
    }

    public void dosyaCoz() throws NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        File file = chooser.showOpenDialog(new Stage());
        if(spnSifrele.isSelected()){
            business.spnFileDecrypt(anahtar.getText(), file);
            durum.setText("Dosya şifre çözme başarılı!");
        }
    }


}