/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */



/* Copyright (c) 2001-2005, The HSQL Development Group
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * Neither the name of the HSQL Development Group nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL HSQL DEVELOPMENT GROUP, HSQLDB.ORG,
 * OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.radixpro.enigma.be.persistency;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

/**
 * Title:        Testdb
 * Description:  simple hello world db example of a
 * standalone persistent db application
 * <p>
 * every time it runs it adds four more rows to sample_table
 * it does a query and prints the results to standard out
 * <p>
 * Author: Karl Meissner karl@meissnersd.com
 */
public class Testdb {

   Connection conn;                                                //our connnection to the db - presist for life of program

   // we dont want this garbage collected until we are done
   public Testdb(String db_file_name_prefix) throws Exception {    // note more general exception

      Class.forName("org.h2.Driver");
      conn = DriverManager.getConnection("jdbc:h2:" + db_file_name_prefix);
   }

   public static void dump(ResultSet rs) throws SQLException {
      ResultSetMetaData meta = rs.getMetaData();
      int colmax = meta.getColumnCount();
      int i;
      Object o = null;
      for (; rs.next(); ) {
         for (i = 0; i < colmax; ++i) {
            o = rs.getObject(i + 1);    // Is SQL the first column is indexed

            // with 1 not 0
            System.out.print(o.toString() + " ");
         }
         System.out.println(" ");
      }
   }

   public static void main(String[] args) {
      Testdb db = null;
      try {
         String userHome = System.getProperty("user.home");
         Properties defaultProps = new Properties();
         FileInputStream in = new FileInputStream(userHome + "/" + ".enigmaastroprops");
         defaultProps.load(in);
         in.close();

         Properties applicationProps = new Properties(defaultProps);
         String dbPath = applicationProps.getProperty("dblocation");
         System.out.println("dbLocation : " + dbPath);

         db = new Testdb(dbPath);
      } catch (Exception ex1) {
         ex1.printStackTrace();    // could not start db

         return;                   // bye bye
      }

      try {
         db.update(
               "CREATE TABLE sample_table ( id INTEGER IDENTITY, str_col VARCHAR(256), num_col INTEGER)");
      } catch (SQLException ex2) {
      }

      try {
         db.update(
               "INSERT INTO sample_table(str_col,num_col) VALUES('Ford', 100)");
         db.update(
               "INSERT INTO sample_table(str_col,num_col) VALUES('Toyota', 200)");
         db.update(
               "INSERT INTO sample_table(str_col,num_col) VALUES('Honda', 300)");
         db.update(
               "INSERT INTO sample_table(str_col,num_col) VALUES('GM', 400)");


         // do a query
         db.query("SELECT * FROM sample_table WHERE num_col < 250");

         // at end of program
         db.shutdown();
      } catch (SQLException ex3) {
         ex3.printStackTrace();
      }
   }    // main()

   public void shutdown() throws SQLException {

      Statement st = conn.createStatement();
      st.execute("SHUTDOWN");
      conn.close();    // if there are no other open connection
   }

   //use for SQL command SELECT
   public synchronized void query(String expression) throws SQLException {

      Statement st = null;
      ResultSet rs = null;

      st = conn.createStatement();         // statement objects can be reused with
      rs = st.executeQuery(expression);    // run the query
      dump(rs);
      st.close();    // NOTE!! if you close a statement the associated ResultSet is
   }

   //use for SQL commands CREATE, DROP, INSERT and UPDATE
   public synchronized void update(String expression) throws SQLException {
      Statement st = null;
      st = conn.createStatement();    // statements
      int i = st.executeUpdate(expression);    // run the query
      if (i == -1) {
         System.out.println("db error : " + expression);
      }
      conn.commit();
      st.close();
   }    // void update()
}    // class Testdb
