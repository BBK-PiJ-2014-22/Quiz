package components;

import tests.GameTest;

import java.rmi.RemoteException;
import java.util.Scanner;

import network.PlayerInterface;
import network.QuizServer;

public class Scratchpad {

	public static void main(String[] args) {
	
		
		QuizServer qs;
		try {
			qs = new QuizServer();
			PlayerInterface pi = (PlayerInterface) qs;
			System.out.println("success");

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		/*
		Scanner sc = new Scanner(System.in);	
		int id = sc.nextInt();
		String name = sc.next();
		
		System.out.println(id);
		System.out.println(name);
		*/
	}
}
