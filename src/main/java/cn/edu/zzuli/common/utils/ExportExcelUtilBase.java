package cn.edu.zzuli.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
/**
 * 导出excel工具类（基础版）
 * @author HUNTER
 *
 * @param <T>
 */
public class ExportExcelUtilBase {
	public final static String df_datetime ="yyyy-MM-dd HH:mm:ss";
	
	public final static String[] StudentHead = new String[]{"学员编号","学员昵称","真实姓名","学员级别","性别","生日","手机号","学员头像","年级编号","邮箱","QQ号","微信号","微博号","注册时间","是否删除","删除时间","是否禁用","禁用时间","行政代码"};
	
	//public final static String[] payInfoHeader = new String[]{"","","","","票号","","车牌号","进场时间","停车时长","计费金额","支付时间","支付金额","","支付流水号","出场时间","创建时间","备注","","支付类型","交易号","发票状态","业务类型","会员名称","停车场名称","详情列表"};
	
	//public final static String[] parkingPaymentHeader = new String[]{"支付时间","支付类型","支付金额","","费用","parkingId","停车场名称","停车费用","停车产品费用","合计"};
	
	//public final static String[] parkingPaymentDayHeader = new String[]{"","","","","","","","支付流水号","支付类型","交易号","支付时间","支付金额","发票状态","业务类型","创建时间","备注","","","","登录名","商户名称","停车场名称","详细支付信息","原始金额"};
	

	/**
	 * 
	 * @param <T>
	 * @param title
	 * 			表格标题名
	 * @param headers
	 * 			表格属性列名数组
	 * @param dataset
	 * 			需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
	 * 			javabean属性的数据类型有基本数据类型及String,Date
	 * @param pattern
	 * 			时间输出格式
	 */
	 @SuppressWarnings("unchecked")
	public static <T> HSSFWorkbook exportExcel(String title, String[] headers,Collection<T> dataset,String pattern){
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 12);
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式  
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);  
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体  
		HSSFFont font = workbook.createFont();  
		font.setColor(HSSFColor.VIOLET.index);  
		font.setFontHeightInPoints((short) 12);  
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式  
		style.setFont(font);
		// 生成并设置另一个样式  
		HSSFCellStyle style2 = workbook.createCellStyle();  
		style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);  
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);  
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);  
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体  
		HSSFFont font2 = workbook.createFont();  
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL); 
		// 把字体应用到当前的样式  
		style2.setFont(font2);
		
		// 产生表格标题行  
		HSSFRow row = sheet.createRow(0);
		for (short i = 0; i < headers.length; i++){  
			HSSFCell cell = row.createCell(i);  
			cell.setCellStyle(style);  
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);  
			cell.setCellValue(text);  
		}
		// 遍历集合数据，产生数据行
		Iterator<T> it = dataset.iterator();
		int index = 0;
		 while (it.hasNext()){
			 index++;
			 row = sheet.createRow(index);
			 T t = (T) it.next();
			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
			 Field[] fields = t.getClass().getDeclaredFields();
			 int nums=0;
			 for (short i = 0; i < fields.length; i++){
				 HSSFCell cell = null;
				 if(nums>0){
					 cell = row.createCell(i-nums);
				 }else{
					 cell = row.createCell(i); 
				 }
				 cell.setCellStyle(style2);
				 Field field = fields[i];
				 String fieldName = field.getName();
				 if(title.equals("UserOrder")){
					 if(fieldName.toLowerCase().equals("devicenumber") || fieldName.toLowerCase().equals("password") ){
						 nums++;
						 continue;
					 }
				 }
				 String getMethodName = "get"
						 + fieldName.substring(0, 1).toUpperCase()
						 + fieldName.substring(1);
				 try{
					 Class tCls = t.getClass();
					 Method getMethod = tCls.getMethod(getMethodName,new Class[]{});
					 Object value = getMethod.invoke(t, new Object[]{});
					 String textValue = "";
					 if(value instanceof Date){
						 if(value!=null && value!=""){
							 Date date = (Date) value;
							 SimpleDateFormat sdf = new SimpleDateFormat(pattern);
							 textValue = sdf.format(date);
						 }
						 
					 }else{
						 if(value!=null && value!=""){
							 textValue= value.toString();
						 }
					 }
					 cell.setCellValue(textValue);
				 }catch(Exception e){
					 e.printStackTrace();
				 }
			 }
		 }
		 return workbook;
	}
	
		/**
		 * 带序号的表格导出
		 * 
		 * @param <T>
		 * @param title
		 * 			表格标题名
		 * @param headers
		 * 			表格属性列名数组
		 * @param dataset
		 * 			需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
		 * 			javabean属性的数据类型有基本数据类型及String,Date
		 * @param pattern
		 * 			时间输出格式
		 */
		 @SuppressWarnings("unchecked")
		public static <T> HSSFWorkbook exportExcelNO(String title, String[] headers,Collection<T> dataset,String pattern){
			// 声明一个工作薄
			HSSFWorkbook workbook = new HSSFWorkbook();
			// 生成一个表格
			HSSFSheet sheet = workbook.createSheet(title);
			// 设置表格默认列宽度为15个字节
			sheet.setDefaultColumnWidth((short) 14);
			// 生成一个样式
			HSSFCellStyle style = workbook.createCellStyle();
			// 设置这些样式  
			style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);  
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			// 生成一个字体  
			HSSFFont font = workbook.createFont();  
			font.setColor(HSSFColor.VIOLET.index);  
			font.setFontHeightInPoints((short) 12);  
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			// 把字体应用到当前的样式  
			style.setFont(font);
			// 生成并设置另一个样式  
			HSSFCellStyle style2 = workbook.createCellStyle();  
			style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);  
			style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
			style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
			style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
			style2.setBorderRight(HSSFCellStyle.BORDER_THIN);  
			style2.setBorderTop(HSSFCellStyle.BORDER_THIN);  
			style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
			style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			// 生成另一个字体  
			HSSFFont font2 = workbook.createFont();  
			font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL); 
			// 把字体应用到当前的样式  
			style2.setFont(font2);
			
			// 产生表格标题行  
			HSSFRow row = sheet.createRow(0);
			for (short i = 0; i < headers.length; i++){  
				HSSFCell cell = row.createCell(i);  
				cell.setCellStyle(style);  
				HSSFRichTextString text = new HSSFRichTextString(headers[i]);  
				cell.setCellValue(text);  
			}
			// 遍历集合数据，产生数据行
			Iterator<T> it = dataset.iterator();
			int index = 0;
			 while (it.hasNext()){
				 index++;
				 row = sheet.createRow(index);
				 T t = (T) it.next();
				// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
				 Field[] fields = t.getClass().getDeclaredFields();
				 for (short i = 0; i <= fields.length; i++){
					 HSSFCell cell = row.createCell(i);
					 cell.setCellStyle(style2);
					 if(i == 0){
						 cell.setCellValue(index);
						 continue;
					 }
					 Field field = fields[i-1];
					 String fieldName = field.getName();
					 String getMethodName = "get"
							 + fieldName.substring(0, 1).toUpperCase()
							 + fieldName.substring(1);
					 try{
						 Class tCls = t.getClass();
						 Method getMethod = tCls.getMethod(getMethodName,new Class[]{});
						 Object value = getMethod.invoke(t, new Object[]{});
						 String textValue = "";
						 if(value instanceof Integer || value instanceof Date){
							 if("status".equals(fieldName)){
								 Integer status = (Integer) value;
								 String aa ="";
								 if(status == 0){
									  aa = "未确认";
								 }else if(status == 1) {
									  aa = "未发货";
								 }else if(status == 2) {
									  aa = "已发货";
								 }
								 textValue=aa.toString();
								 
							 }
							 if("createtime".equals(fieldName) && value!=null && value!=""){
								 Date date = (Date) value;
								 SimpleDateFormat sdf = new SimpleDateFormat(pattern);
								 textValue = sdf.format(date);
							 }
							 
						 }else{
							 if(value!=null && value!=""){
								 textValue= value.toString();
							 }
						 }
						 cell.setCellValue(textValue);
					 }catch(Exception e){
						 e.printStackTrace();
					 }
				 }
			 }
			 return workbook;
		}
	 
}
