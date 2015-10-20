package de.canitzp.hosenlauncher;

import de.canitzp.hosenlauncher.controller.SettingsController;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Save extends File{

    private static String saveFile;
    private static Map toSaveMap = new HashMap<>();
    public static Map to = new HashMap<>();

    public Save(String path){
        super(path);
        saveFile = path;
    }

    public Save addToList(String value, Object c){
        to.put(value, c);
        return this;
    }

    public void savePassword(String password) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        String keyStr = Key.key;
        byte[] key = (keyStr).getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("MD5");
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encrypted = cipher.doFinal(password.getBytes());
        BASE64Encoder myEncoder = new BASE64Encoder();
        String geheim = myEncoder.encode(encrypted);
        BASE64Decoder myDecoder2 = new BASE64Decoder();
        byte[] crypted2 = myDecoder2.decodeBuffer(geheim);
        to.put("password", crypted2);
    }

    public void save() throws IOException {
        new File(saveFile).getParentFile().mkdirs();
        if(!new File(saveFile).exists()) new File(saveFile).createNewFile();
        FileOutputStream fos = new FileOutputStream(saveFile);
        ObjectOutputStream ous = new ObjectOutputStream(fos);
        ous.writeObject(to);
        ous.close();
        fos.close();
    }

    public Object read(String value) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(saveFile);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Map map = (Map) ois.readObject();
        ois.close();
        fis.close();
        return map.get(value);
    }

    public String readPassword(){
        try {
            Object o = read("password");
            String keyStr = Key.key;
            byte[] key = (keyStr).getBytes("UTF-8");
            MessageDigest sha = MessageDigest.getInstance("MD5");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher2 = Cipher.getInstance("AES");
            cipher2.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] cipherData2 = cipher2.doFinal((byte[])o);
            String erg = new String(cipherData2);
            return erg;
        } catch (IOException | InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveVariables() {
        try {
            if (Variables.username != null || !Objects.equals(Variables.username, "")) {
                addToList("username", Variables.username);
            } else newVars();
            if (Variables.password != null || !Objects.equals(Variables.password, "")) {
                savePassword(Variables.password);
            } else newVars();
            if (!Objects.equals(Integer.toString(Variables.maxRam), "")) {
                addToList("maxRam", Variables.maxRam);
            } else newVars();
            if (!Objects.equals(Integer.toString(Variables.minRam), "")) {
                addToList("minRam", Variables.minRam);
            } else newVars();
            addToList("debug", Variables.debug);
            save();
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    private void newVars(){
        addToList("username", null);
        addToList("password", null);
        addToList("maxRam", 1024);
        addToList("minRam", 128);
        addToList("debug", false);
        try {
            save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getVariables() {
        Variables.username = Variables.settingsController.userField.getText();
        Variables.password = Variables.settingsController.passField.getText();
        Variables.maxRam = Integer.parseInt(Variables.settingsController.ramMax.getText());
        Variables.minRam = Integer.parseInt(Variables.settingsController.ramMin.getText());
        Variables.debug = Variables.settingsController.debugBool.isSelected();
    }
}
