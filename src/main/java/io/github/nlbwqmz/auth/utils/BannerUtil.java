package io.github.nlbwqmz.auth.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BannerUtil {

  private final static String BANNER =
      " _    _   ___         ___  _   _ _____ _   _ \n"
          + "| |  | | |_  |       / _ \\| | | |_   _| | | |\n"
          + "| |  | |   | |______/ /_\\ \\ | | | | | | |_| |\n"
          + "| |/\\| |   | |______|  _  | | | | | | |  _  |\n"
          + "\\  /\\  /\\__/ /      | | | | |_| | | | | | | |\n"
          + " \\/  \\/\\____/       \\_| |_/\\___/  \\_/ \\_| |_/\n"
          + "v0.0.4";


  public static void printBanner() {
    log.info(BANNER);
  }

}
