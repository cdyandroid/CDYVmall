package parfois.com.cdyvmall.adapter.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Environment;

public class ReadWriteUtils {
	private static File parentFile = new File(Environment.getExternalStorageDirectory()+File.separator+"VmallTest");

	public static boolean isReadWrite() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			parentFile.mkdir();
			return true;
		}
		return false;
	}

	public static boolean hasFile(String name) {
		File file = new File(parentFile,name);
		if (file.exists()) {
			return true;
		}
		return false;
	}

	public static boolean writeImg(byte[] buff,String name) {
		boolean flag = false;
		if (isReadWrite()) {
			File file = new File(parentFile, name);
			FileOutputStream out = null;
			try {
				out = new FileOutputStream(file);
				out.write(buff);
				out.flush();
				out.close();
				flag = true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	public static byte[] readImg(String name) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		if (isReadWrite()) {
			if (hasFile(name)) {
				File file=new File(parentFile, name);
				FileInputStream in = null;
				try {
					in = new FileInputStream(file);
					int temp = 0;
					byte[] buff = new byte[1024];
					while ((temp = in.read(buff)) != -1) {
						out.write(buff, 0, temp);
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return out.toByteArray();
	}

}
