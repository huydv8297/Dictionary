package com.example.dictionary.Utils;

import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Algorithm {
  public static String FileNameFromUri(Uri uri, boolean keepExtension) {
    final String pathFromRoot = UriToPathFromRoot(uri);
    String fileName = pathFromRoot.substring(pathFromRoot.lastIndexOf("/") + 1);
    if (!keepExtension) fileName = fileName.substring(0, fileName.lastIndexOf("."));
    return fileName;
  }

  public static String ReplaceUriFileName(Uri uri, String newFileName) {
    final StringBuffer oldPath = new StringBuffer(UriToPathFromRoot(uri));
    final String oldFileNameBuffer = FileNameFromUri(uri, true);
    final StringBuffer newPath = oldPath.replace(oldPath.lastIndexOf(oldFileNameBuffer), oldPath.length(), newFileName);
    return newPath.toString();
  }

  public static String UriToPathFromRoot(Uri uri) {
    final String uriPath = uri.getPath();
    return uriPath.substring(uriPath.lastIndexOf(":") + 1);
  }

  public static byte[] ReadFileToBytes(String path) throws IOException {
    return ReadFileToBytes(new FileInputStream(path));
  }

  public static byte[] ReadFileToBytes(InputStream inputStream) throws IOException {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    byte[] buffer = new byte[64 * 1024];
    while (inputStream.available() > 0) {
      int bytesRead = inputStream.read(buffer);
      outputStream.write(buffer, 0, bytesRead);
    }
    byte[] bytes = outputStream.toByteArray();
    inputStream.close();
    outputStream.close();
    return bytes;
  }
}
