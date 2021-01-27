package com.manson.fo76;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;
import org.apache.commons.io.FileUtils;
import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.ProcessResult;

public class Main {

  private static String ARCHIVE2_TOOL = "D:\\Program Files (x86)\\Bethesda.net Launcher\\games\\Tools\\Archive2\\Archive2.exe";
  private static String PATH_TO_BA2_FILE = "E:\\BethesdaGames\\Fallout76\\Data\\SeventySix - Interface.ba2";
  private static String PATH_TO_FFDEC = "D:\\Program Files (x86)\\FFDec\\ffdec.bat";

  private static String PATH_TO_TMP_SWF_DIR = "output";
  private static String PATH_TO_FLA_OUTPUT_DIR = "dump";

  private static List<String> BA2_SUBDIRS = Arrays.asList("interface", "programs");


  public static void main(String[] args) throws Exception {
    ARCHIVE2_TOOL = System.getProperty("ARCHIVE2_TOOL");
    PATH_TO_BA2_FILE = System.getProperty("PATH_TO_BA2_FILE");
    PATH_TO_FFDEC = System.getProperty("PATH_TO_FFDEC");
    PATH_TO_TMP_SWF_DIR = System.getProperty("PATH_TO_TMP_SWF_DIR");
    PATH_TO_FLA_OUTPUT_DIR = System.getProperty("PATH_TO_FLA_OUTPUT_DIR");
    BA2_SUBDIRS = Arrays.asList(System.getProperty("BA2_SUBDIRS").split(","));
    try {
      FileUtils.forceDelete(new File(PATH_TO_FLA_OUTPUT_DIR));
    } catch (Exception e) {
      System.err.println("Error removing previous patch data, output may be messed up");
      e.printStackTrace();
    }
    try {
      File swfOutput = new File(PATH_TO_TMP_SWF_DIR);
      File flaOutput = new File(PATH_TO_FLA_OUTPUT_DIR);
      swfOutput.mkdirs();
      flaOutput.mkdirs();
      extractBa2Archive(PATH_TO_BA2_FILE, swfOutput.getAbsolutePath());
      BA2_SUBDIRS.parallelStream().forEach(x -> {
        File outputDir = new File(flaOutput, x);
        outputDir.mkdirs();
        convertSwfToFla(new File(swfOutput, x).getAbsolutePath(), outputDir.getAbsolutePath());
      });
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      Thread.sleep(2000);
      FileUtils.forceDelete(new File(PATH_TO_TMP_SWF_DIR));
    }
  }


  private static void extractBa2Archive(String inputFile, String outputPath)
      throws Exception {
    File input = new File(inputFile);
    execute(ARCHIVE2_TOOL, input.getAbsolutePath(), "-e=" + outputPath);
  }

  private static void convertSwfToFla(String input, String output) {
    try {
      execute(PATH_TO_FFDEC, "-export", "fla", output, input);
    } catch (Exception e) {
      System.err.println("Error converting to fla");
      e.printStackTrace();
    }
  }

  private static ProcessResult execute(String... command) throws InterruptedException, TimeoutException, IOException {
    return new ProcessExecutor()
        .command(command)
        .redirectOutput(System.out)
        .redirectError(System.err)
        .execute();
  }

}
