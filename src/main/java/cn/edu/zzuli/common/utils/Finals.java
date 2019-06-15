package cn.edu.zzuli.common.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Finals {

  private final static Logger logger       = LoggerFactory.getLogger(Finals.class);
  
  public static String        baseEnablingUrl;

  public static String        enablingApplyUrl;

  public static String        enablingUploadUrl;

  public static String        enablingQueryUrl;

  public static String        enablingDeleteUrl;

  public static String        enablingUpdateUrl;

  public static String        enablingCdnUrl;

  public static String        gtAppId;

  public static String        gtAppKey;

  public static String        gtMasterSecret;

  public static String        gtUrl        = "http://sdk.open.api.igexin.com/serviceex";

  public static String        savepath;

  public static String        publishpath;

  public static String        addPageStaticUrl;

  public static String        opserviceAppId;

  public static String        opserviceAppVersion;

  public static String        opserviceClientId;

  public static String        gtMMPurl;

  public static String        gtMMPport;

  public static String loginUrl;
  public static String sequenceId;
  public static String accType;
  public static String loginType;
  public static String thirdpartyAppId;
  public static String thirdpartyAccessToken;
  public static String appId;
  public static String appkey;
  public static String systemID;
  public static String clientId;
  public static String appVersion;
  public static String contentType;
  public static String appServerUrl;

  private static Properties   props_config = new Properties();
  public static StringBuffer  template;

  static {

    try {

      InputStream ips = new BufferedInputStream(Finals.class.getResourceAsStream("/config.properties"));

      props_config.load(ips);
      ips.close();

      if (props_config.getProperty("base_enabling_url") != null) {
        baseEnablingUrl = props_config.getProperty("base_enabling_url");
      }
      if (props_config.getProperty("enabling_apply_url") != null) {
        enablingApplyUrl = props_config.getProperty("enabling_apply_url");
      }
      if (props_config.getProperty("enabling_upload_url") != null) {
        enablingUploadUrl = props_config.getProperty("enabling_upload_url");
      }
      if (props_config.getProperty("enabling_query_url") != null) {
        enablingQueryUrl = props_config.getProperty("enabling_query_url");
      }
      if (props_config.getProperty("enabling_delete_url") != null) {
        enablingDeleteUrl = props_config.getProperty("enabling_delete_url");
      }
      if (props_config.getProperty("enabling_update_url") != null) {
        enablingUpdateUrl = props_config.getProperty("enabling_update_url");
      }
      if (props_config.getProperty("enabling_cdn_url") != null) {
        enablingCdnUrl = props_config.getProperty("enabling_cdn_url");
      }
      if (props_config.getProperty("gtAppId") != null) {
        gtAppId = props_config.getProperty("gtAppId");
      }
      if (props_config.getProperty("gtAppKey") != null) {
        gtAppKey = props_config.getProperty("gtAppKey");
      }
      if (props_config.getProperty("gtMasterSecret") != null) {
        gtMasterSecret = props_config.getProperty("gtMasterSecret");
      }
      if (props_config.getProperty("gtUrl") != null) {
        gtUrl = props_config.getProperty("gtUrl");
      }
      if (props_config.getProperty("savepath") != null) {
        savepath = props_config.getProperty("savepath");
      }

      if (props_config.getProperty("publishpath") != null) {
        publishpath = props_config.getProperty("publishpath");
      }

      if (props_config.getProperty("addPageStaticUrl") != null) {
        addPageStaticUrl = props_config.getProperty("addPageStaticUrl");
      }

      if (props_config.getProperty("opserviceAppId") != null) {
        opserviceAppId = props_config.getProperty("opserviceAppId");
      }
      if (props_config.getProperty("opserviceAppVersion") != null) {
        opserviceAppVersion = props_config.getProperty("opserviceAppVersion");
      }
      if (props_config.getProperty("opserviceClientId") != null) {
        opserviceClientId = props_config.getProperty("opserviceClientId");
      }
      if (props_config.getProperty("gtMMPurl") != null) {
        gtMMPurl = props_config.getProperty("gtMMPurl");
      }
      if (props_config.getProperty("gtMMPport") != null) {
        gtMMPport = props_config.getProperty("gtMMPport");
      }
      if (props_config.getProperty("loginUrl") != null) {
        loginUrl = props_config.getProperty("loginUrl");
      }

      if (props_config.getProperty("sequenceId") != null) {
        sequenceId = props_config.getProperty("sequenceId");
      }
      if (props_config.getProperty("accType") != null) {
        accType = props_config.getProperty("accType");
      }
      if (props_config.getProperty("loginType") != null) {
        loginType = props_config.getProperty("loginType");
      }
      if (props_config.getProperty("thirdpartyAppId") != null) {
        thirdpartyAppId = props_config.getProperty("thirdpartyAppId");
      }
      if (props_config.getProperty("thirdpartyAccessToken") != null) {
        thirdpartyAccessToken = props_config.getProperty("thirdpartyAccessToken");
      }
      if (props_config.getProperty("appId") != null) {
        appId = props_config.getProperty("appId");
      }
      if (props_config.getProperty("appkey") != null) {
        appkey = props_config.getProperty("appkey");
      }
      if (props_config.getProperty("systemID") != null) {
        systemID = props_config.getProperty("systemID");
      }
      if (props_config.getProperty("clientId") != null) {
        clientId = props_config.getProperty("clientId");
      }
      if (props_config.getProperty("appVersion") != null) {
        appVersion = props_config.getProperty("appVersion");
      }
      if (props_config.getProperty("contentType") != null) {
        contentType = props_config.getProperty("contentType");
      }
      if (props_config.getProperty("appServerUrl") != null) {
        appServerUrl = props_config.getProperty("appServerUrl");
        }
      template = readFromFile();
    } catch (Exception e) {
      logger.error(e.toString());
    }

  }

  private static StringBuffer readFromFile() throws FileNotFoundException, IOException {
    File file = new File(Finals.class.getResource("/temp.html").getPath());// 指定要读取的文件
    FileReader reader = new FileReader(file);// 获取该文件的输入流
    char[] bb = new char[1024];// 用来保存每次读取到的字符
    StringBuffer str = new StringBuffer();// 用来将每次读取到的字符拼接，当然使用StringBuffer类更好
    int n;// 每次读取到的字符长度
    while ((n = reader.read(bb)) != -1) {
      str.append(new String(bb, 0, n));
    }
    reader.close();// 关闭输入流，释放连接
    return str;
  }
}
