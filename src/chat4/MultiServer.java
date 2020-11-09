package chat4; //기능에 대한 변화보다는 역할에 대한 변화가 있다!

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiServer 
{
	//멤버변수
	static ServerSocket serverSocket = null;
	static Socket socket = null;
	static PrintWriter out = null;
	static BufferedReader in = null;
	static String s = ""; 
	//생성자
	public MultiServer()
	{
		//실행부없음
	}
	
	//서버의 초기화를 담당할 메소드 (보통 init이란 초기화란 뜻이 있다)
	public static void init()
	{
		String name = ""; //클라이언트의 이름을 저장
		
		try
		{
			//클라이언트의 접속을 대기
			serverSocket = new ServerSocket(9999);
			System.out.println("서버가 시작되었습니다.");
		
			//클라이언트의 접속을 허가
			socket = serverSocket.accept();
			System.out.println(socket.getInetAddress() + ":" + socket.getPort());
			
			//클라이언트로 메세지를 보낼 준비(서버 입장에서) output스트림
			out = new PrintWriter(socket.getOutputStream(), true);
			//클라이언트가 보내주는 메세지를 읽을 준비(서버 입장에서) input스트림
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
			
			//클라이언트가 최초로 전송하는 메세지는 이름이다!
			if(in != null) //최초로 들어온 사람이 null값이 아닐 때 실행!
			{
				name = in.readLine(); //클라이언트의 이름을 읽어서 저장
				System.out.println(name+ "접속"); //접속자명을 서버의 콘솔에 출력
				out.println("> "+ name +"님이 접속했습니다."); //클라이언트에게 Echo 입력
			}
			
			//클라이언트가 전송하는 메세지를 계속해서 읽어온다
			while(in != null) //실제로 대화를 하는 부분(while문)
			{
				s = in.readLine();
				if(s==null)
				{
					break;
				}
				System.out.println(name +" ==> "+ s); //읽어온 메세지를 서버의 콘솔에 출력
				sendAllMsg(name, s); //클라이언트에게 Echo 입력
			}
			
			System.out.println("Bye...!!!");
		}
	
		catch (Exception e)
		{
			System.out.println("예외1 : "+ e);
			//e.printStackTrace(); 활성화 시켜도 상관없지만 채팅내용을 봐야하기 때문에 주석처리함
		}
		
		finally
		{
			try
			{
				//입출력 스트림 종료(자원해제)
				in.close();
				out.close();
				//소켓 종료(자원해제)
				socket.close();
				serverSocket.close();
			}
			catch (Exception e)
			{
				System.out.println("예외2 : "+ e);
				//e.printStackTrace();
			}
		}
	}	
		
	//서버가 클라이언트에게 메세지를 Echo해주는 메소드
	public static void sendAllMsg(String name, String msg)
	{
		try
		{
			out.println("> " + name + " ==> " + msg);
		}
		catch (Exception e)
		{
			System.out.println("예외 : "+ e);
		}
	}
		
	//main()은 프로그램의 출발점 역할만 담당한다
	public static void main(String[] args)
	{
		init();
	}
	
}	
