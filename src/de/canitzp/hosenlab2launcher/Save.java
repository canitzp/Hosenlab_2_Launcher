package de.canitzp.hosenlab2launcher;

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
        String keyStr = MD5Key.key;
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
        Object from = map.get(value);
        if(!Objects.equals(value, "password"))System.out.println(from);
        return from;
    }

    public String readPassword(){
        try {
            Object o = read("password");
            String keyStr = MD5Key.key;
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

    public void saveVariables(){
        try {
            if(Main.username != null || !Objects.equals(Main.username, "")){
                addToList("username", Main.username);
            } else deleteSave();
            if(Main.password != null || !Objects.equals(Main.password, "")){
                savePassword(Main.password);
            } else deleteSave();
            if(!Objects.equals(Integer.toString(Main.maxRam), "")){
                addToList("maxRam", Main.maxRam);
            } else deleteSave();
            if(!Objects.equals(Integer.toString(Main.minRam), "")){
                addToList("minRam", Main.minRam);
            } else deleteSave();
            if(Main.java != null || !Objects.equals(Main.java, "")){
                addToList("java", Main.java);
            } else deleteSave();
            save();
        } catch (Throwable e) {
            deleteSave();
            e.printStackTrace();
        }
    }

    public void deleteSave(){
        Main.save.delete();
        Main.stop();
    }
    public void deleteSaveRestart(){
        new File(saveFile).delete();
        Main.main(new String[]{"notNull"});
        Main.window.addToTextArea("Corrupted Save-File!");
    }
}
