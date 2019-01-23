package org.integratedmodelling.mca.contrib.j_electre.cycles;

//	Copyright � 2017 by Valdecy Pereira, Helder Gomes Costa and Livia Dias de Oliveira Nepomuceno.

//	BreadthFirstSearchAlgorithm_BFS: Algorithm by D. B. Johnson Finding all the elementary circuits of a directed graph. D. B. Johnson, SIAM Journal on Computing 4, no. 1, 77-84, 1975. http://dx.doi.org/10.1137/0204007
//	BreadthFirstSearchAlgorithm_BFS: Copyright @ 26.08.2006 by Frank Meyer - web@normalisiert.de or https://github.com/josch/cycles_johnson_meyer.
//	BreadthFirstSearchAlgorithm_BFS: Modified in 01.01.2017 by Valdecy Pereira, Helder Gomes Costa and Livia Dias de Oliveira Nepomuceno.

//	This file is part of J-ELECTRE.
//
//	J-ELECTRE is free software: you can redistribute it and/or modify
//	it under the terms of the GNU General Public License as published by
//	the Free Software Foundation, either version 3 of the License, or
//	(at your option) any later version.
//
//	J-ELECTRE is distributed in the hope that it will be useful,
//	but WITHOUT ANY WARRANTY; without even the implied warranty of
//	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//	GNU General Public License for more details.
//
//	You should have received a copy of the GNU General Public License
//	along with J-ELECTRE.  If not, see <http://www.gnu.org/licenses/>.

public class BreadthFirstSearchAlgorithm_BFS {

	class Node {
		int label; // vertex label
		Node next; // next node in list

		Node(int b) // constructor
		{
			label = b;
		}
	}

	class Graph {
		int size;
		Node adjList[];
		int mark[];

		Graph(int n) // constructor
		{
			size = n;
			adjList = new Node[size];
			mark = new int[size];
		}

		public void createAdjList(int a[][]) // create adjacent lists
		{
			Node p;
			int i, k;
			for (i = 0; i < size; i++) {
				p = adjList[i] = new Node(i);
				for (k = 0; k < size; k++) {
					if (a[i][k] == 1) {
						p.next = new Node(k);
						p = p.next;
					}
				} 
			}
		} 

		public void bfs(int head) {
			int v;
			Node adj;
			Queue q = new Queue(size);
			v = head;
			mark[v] = 1;
			System.out.print(v + "");
			q.qinsert(v);
			while (!q.IsEmpty()) // while(queue not empty)
			{
				v = q.qdelete();
				adj = adjList[v];
				while (adj != null) {
					v = adj.label;
					if (mark[v] == 0) {
						q.qinsert(v);
						mark[v] = 1;
						System.out.print(v + "");
					}

					adj = adj.next;
				}
			}
		}
	}

	class Queue {
		private int maxSize; // max queue size
		private int[] que; // it is an array of integers
		private int front;
		private int rear;
//		private int count; // count of items in queue

		public Queue(int s) // constructor
		{
			maxSize = s;
			que = new int[maxSize];
			front = rear = -1;
		}

		public void qinsert(int item) {
			if (rear == maxSize - 1)
				System.out.println("Queue is Full");
			else {
				rear = rear + 1;
				que[rear] = item;
				if (front == -1)
					front = 0;
			}
		}

		public int qdelete() {
			int item;
			if (IsEmpty()) {
				System.out.println("\n Queue is Empty");
				return (-1);
			}
			item = que[front];
			if (front == rear)
				front = rear = -1;
			else
				front = front + 1;
			return (item);
		}

		public boolean IsEmpty() {
			return (front == -1);
		}
	}
		public static void main(String[] args) {
		}
	}
