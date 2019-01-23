package org.integratedmodelling.mca.contrib.j_electre.ui;

//	Copyright � 2017 by Valdecy Pereira, Helder Gomes Costa and Livia Dias de Oliveira Nepomuceno.

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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JTable;
import javax.swing.table.TableModel;

public class Export {

	public static void xt(JTable table, File txt) throws IOException {
		try{
			TableModel model_1  = table.getModel();
			FileWriter export = new FileWriter(txt);
			for(int i = 0; i < model_1.getRowCount(); i++) {
				for(int j = 0; j < model_1.getColumnCount(); j++) {
					if(model_1.getValueAt(i,j) != null && model_1.getValueAt(i,j).toString().trim().length() != 0){
						export.write(model_1.getValueAt(i,j).toString()+ "\t");
					}		
				}
				export.write("\n");
			}
			export.close();
		}catch(IOException e2){ System.out.println(e2); }
	}
}
