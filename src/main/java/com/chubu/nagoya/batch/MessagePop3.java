package com.chubu.nagoya.batch;

import java.io.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.*;

import javax.mail.internet.MimeBodyPart;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.chubu.nagoya.member.model.dto.MessageInfo;

@Component
public class MessagePop3 {
	
		private static final String HOST = "pop.gmail.com";
		private static final String USERNAME = "ngaydeptroi1000@gmail.com";
		private static final String PASSWORD = "Nihongo37";
		private static final String MAILFROM = "pasonatech.jp";
		private static final String FOLDER = "C:\\Users\\Administrator\\Desktop\\ha\\";

		public static ArrayList<MessageInfo> listMessage() throws MessagingException, IOException, ParseException {
			Folder folder = null;
			Store store = null;

			ArrayList<String> listAllLine = new ArrayList<String>();
			ArrayList<MessageInfo> listInfor = new ArrayList<MessageInfo>();

			try {
				Properties props = new Properties();
				props.put("mail.store.protocol", "pop3s"); // Google uses POP3S not POP3
				Session session = Session.getDefaultInstance(props);
				// session.setDebug(true);
				store = session.getStore();
				store.connect(HOST, USERNAME, PASSWORD);
				folder = store.getDefaultFolder().getFolder("INBOX");
				folder.open(Folder.READ_ONLY);
				Message[] messages = folder.getMessages();
				System.out.println("No of Messages : " + folder.getMessageCount());
				System.out.println("No of Unread Messages : " + folder.getUnreadMessageCount());

				System.out.println(messages.length);

				for (int i = 0; i < messages.length; ++i) {

//					System.out.println("MESSAGE #" + (i + 1) + ":");
					Message msg = messages[i];

					Address address = msg.getFrom()[0];
					String add = String.valueOf(address);

					String[] output = add.split("<");
					String mailInBox = output[output.length - 1];
					mailInBox = mailInBox.split(">")[0];
					
					String mailFrom = mailInBox.split("@")[1];
					
					if (MAILFROM.equals(mailFrom)) {

//						String from = "unknown";
//						if (msg.getReplyTo().length >= 1) {
//							from = msg.getReplyTo()[0].toString();
//						} else if (msg.getFrom().length >= 1) {
//							from = msg.getFrom()[0].toString();
//						}
						String subject = msg.getSubject();
//						System.out.println("File ... " + subject + " " + from);
						// you may want to replace the spaces with "_"
						// the files will be saved into the TEMP directory
						String filename = FOLDER + subject;
						listAllLine = saveParts(msg.getContent(), filename);
						listInfor.add(getInformation(listAllLine, mailInBox));

					}

				}
				return listInfor;
				
			} finally {
				if (folder != null) {
					folder.close(true);
				}
				if (store != null) {
					store.close();
				}
			}
		}

		public static ArrayList<String> saveParts(Object content, String path) throws IOException, MessagingException {
			OutputStream out = null;
//			InputStream in = null;
			Reader in = null;
			ArrayList<String> listLine = new ArrayList<String>();
			try {
				if (content instanceof Multipart) {
					Multipart multi = ((Multipart) content);
					int parts = multi.getCount();
					String filename;
					for (int j = 0; j < parts; ++j) {

						filename = path;
						MimeBodyPart part = (MimeBodyPart) multi.getBodyPart(j);
						if (part.getContent() instanceof Multipart) {
							// part-within-a-part, do some recursion...
							saveParts(part.getContent(), filename);
						} else {
							String extension = "";
							if (part.isMimeType("text/calendar")) {
								extension = "ics";
							} else if (part.isMimeType("text/plain")) {
								extension = "txt";
							} else {
								// Try to get the name of the attachment
								extension = "";
							}
							if (!(extension.equals(""))) {

//								in = part.getInputStream();
								InputStreamReader isr = new InputStreamReader(part.getInputStream(), "UTF8");
								in = new BufferedReader(isr);
								
								String str = "";
								int k;
								char charBuff;

								while ((k = in.read()) != -1) {

									if (k == '\n') {
										
										if(str.charAt(0) == '\n') {
											str = str.substring(1);
											
										}
										str = str.replace("\r", "");
										listLine.add(str);
										str = "";
									}
									charBuff = (char) k;
									str += charBuff + "";
								}
							}
						}
					}
				}
				return listLine;

			} finally {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.flush();
					out.close();
				}
			}
		}

		public static MessageInfo getInformation(ArrayList<String> listLine, String mailInBox) throws ParseException {

			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
			Date date;
			
			String patternTitle = "(?:SUMMARY;).*:(.*)";
			String patternDescript = "(?:DESCRIPTION;).*:(.*)";
			String patternStart = "(?:DTSTART;).*:(\\w{15})";
			String patternEnd = "(?:DTEND;).*:(\\w{15})";	
			String patternLocation = "(?:LOCATION;).*:(.*)";
			
			Pattern dtTitle = Pattern.compile(patternTitle);
			Pattern dtDescript = Pattern.compile(patternDescript);
			Pattern dtStart = Pattern.compile(patternStart);
			Pattern dtEnd = Pattern.compile(patternEnd);
			Pattern dtLocation = Pattern.compile(patternLocation);
			
			MessageInfo obj = new MessageInfo();
			
			int j = 0;
			for (int i = 0; i < listLine.size(); i++) {
				
				Matcher title = dtTitle.matcher(listLine.get(i));
				Matcher descript = dtDescript.matcher(listLine.get(i));
				Matcher start = dtStart.matcher(listLine.get(i));
				Matcher end = dtEnd.matcher(listLine.get(i));
				Matcher location = dtLocation.matcher(listLine.get(i));
				
				if (title.find()) {	
					
					obj.setTitle(title.group(1));
					
				} else if (descript.find()) {	
					
					//get all of content of description
					j = i + 1;
					String str = descript.group(1);
					while(!listLine.get(j).substring(0,3).equals("UID")) {
						str = str + listLine.get(j).substring(1);					
						j = j + 1;
					}
					obj.setDescript(str);
								
				} else if (start.find()) {	
					
					date = formatter.parse(start.group(1));
					obj.setStartTime(date);
					
				} else if (end.find()) {	
					
					date = formatter.parse(end.group(1));
					obj.setEndTime(date);	
					
				} 	else if (location.find()) {	
					
					obj.setLocation(location.group(1));	
					
				} 
			}
			
			obj.setMailFrom(mailInBox);
			
			System.out.println(obj.getTitle());	
			System.out.println(obj.getDescript());	
			System.out.println(obj.getStartTime());	
			System.out.println(obj.getEndTime());

			return obj;
		}

		@Scheduled(fixedRate =90000)
		public static void report() throws 	Exception {
			
			Class.forName("org.postgresql.Driver");

			Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/demo", "postgres", "postgres");
			
			
			PreparedStatement stmt = null;
			String sql = "INSERT INTO table_messeage(title, descript, starttime, endtime,mailfrom,location)"
					+ "VALUES(?,?,?,?,?,?)";
			
			connection.setAutoCommit(false);
					
			long startTime = System.currentTimeMillis();
			List<MessageInfo> listMes = listMessage();
			System.out.println("Batch - Inserting... ");
			System.out.println("Size list... " + listMes.size());
			for (int i = 0; i< listMes.size(); i++) {

				stmt = connection.prepareStatement(sql); 
				Timestamp startTimeStamp = new Timestamp(listMes.get(i).getStartTime().getTime());
				Timestamp endTimeTimeStamp = new Timestamp(listMes.get(i).getEndTime().getTime());
				
				stmt.setString(1,listMes.get(i).getTitle());
				stmt.setString(2,listMes.get(i).getDescript());
				stmt.setTimestamp(3, startTimeStamp);
				stmt.setTimestamp(4, endTimeTimeStamp);
				stmt.setString(5,listMes.get(i).getMailFrom());
				stmt.setString(6,listMes.get(i).getLocation());
				
				stmt.executeUpdate();

			}
			connection.commit();			
			connection.close();
			long endTime = System.currentTimeMillis();
			System.out.println("Total time: " + (endTime - startTime));
			
		}
	}