/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.itson.catrina.business;


import mx.itson.catrina.enums.Type;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import mx.itson.catrina.entities.AccountStatement;
import mx.itson.catrina.entities.Movements;

/**
 * Contains the operations to make the program work.
 * @author José Alvarez
 */
public class Operations {
    /**
     * Display filtered data in a JTable. 
     * @param transactions
     * @param currencyFormat
     * @param tblRegisters The table where the values will be displayed.
     * @param initialBalance Initial data to obtain the subtotal.
     * A default table model is created from the provided table model (tblRegisters).
     * The for loop iterates over each "Movements" object in the "transactions" list.
     * On the for loop is an if-else control structure that checks the type of movement "Type" in each "Movements" object.
     * If the type is deposit, increase the balance "sum" with the deposit amount.
     * If the type is withdrawal, decreases the balance (sum) with the withdrawal amount.
     */
   public void showFilteredData(List<Movements> transactions,NumberFormat currencyFormat, JTable tblRegisters, double initialBalance){
       DefaultTableModel model = (DefaultTableModel) tblRegisters.getModel();
       model.setRowCount(0);              
       DateFormat dateFormat = new SimpleDateFormat("dd/MMMM/yyyy");
       double sum = initialBalance;
           for(Movements i : transactions){                   
               if(i.getType()==Type.DEPOSIT){
                   sum+= i.getAmount();
                   model.addRow(new Object[] { dateFormat.format(i.getDate()),i.getDescription(), currencyFormat.format(i.getAmount()), currencyFormat.format(0), currencyFormat.format(sum)});                        
                }else if(i.getType()==Type.WITHDRAWAL){
                       sum-= i.getAmount();
                       model.addRow(new Object[] { dateFormat.format(i.getDate()),i.getDescription(), currencyFormat.format(0), currencyFormat.format(i.getAmount()), currencyFormat.format(sum)});
                    }
                }   
   }
   /**
    * Shows transaction data.
    * @param accountStatement It is used to obtain the list of movements.
    * @param currencyFormat Representative format for numerical quantities.
    * @param tblRegisters The table where the values will be displayed.
    * The "showData" function prepares the table to display data, sorts the transactions in the statement by date, and then iterates over each transaction.
    * Depending on the type of transaction "deposit" or "withdrawal", a subtotal is updated and a new row is added to the table with the information of the date,
    * description, deposit amount, withdrawal amount, and the updated subtotal.
    * The resulting table shows transactions arranged chronologically with balance details.
    */
   public void showData(AccountStatement accountStatement,NumberFormat currencyFormat, JTable tblRegisters){
       DefaultTableModel model = (DefaultTableModel) tblRegisters.getModel();
       model.setRowCount(0);              
       DateFormat dateFormat = new SimpleDateFormat("dd/MMMM/yyyy");
       accountStatement.getTransactions().sort((m1,m2)->m1.getDate().compareTo(m2.getDate()));
       double subTotal = 0;
           for(Movements i : accountStatement.getTransactions()){                   
               if(i.getType()==Type.DEPOSIT){
                   subTotal += i.getAmount();
                   model.addRow(new Object[] { dateFormat.format(i.getDate()),i.getDescription(), currencyFormat.format(i.getAmount()), currencyFormat.format(0), currencyFormat.format(subTotal)});                        
                }else if(i.getType()==Type.WITHDRAWAL){
                       subTotal -= i.getAmount();
                       model.addRow(new Object[] { dateFormat.format(i.getDate()),i.getDescription(), currencyFormat.format(0), currencyFormat.format(i.getAmount()), currencyFormat.format(subTotal)});
                    }
                }   
   }
   /**
    * Make a sum of all deposits.
    * @param transactions List of movements used as a reference to traverse data.
    * @return The sum of all deposits.
    * The "sumDeposits" function receives a list of financial transactions represented by Movements objects.
    * Calculates and returns the total sum of the amounts associated with transactions of type "deposit" in the provided list.
    * The function iterates through the list, accumulates the deposit amounts, and returns the result.
    */
   public Double sumDeposits(List<Movements> transactions ){     
        double sum = 0;
        for(Movements m : transactions){
            if(m.getType()==Type.DEPOSIT){
                sum+= m.getAmount();
            }
        }
         return sum;       
    }
   /**
    * Make a sum of all withdrawals.
    * @param transactions List of movements used as a reference to traverse data.
    * @return The sum of all withdrawals.
    * The "sumWithDrawals" function takes a list of financial transactions represented by "Movements" objects as input.
    * Calculates and returns the total sum of amounts associated with transactions of type "withdrawal" in the provided list.
    * The function iterates through the list, accumulates the withdrawal amounts, and returns the result as a value of type double.
    */
   public Double sumWithDrawals(List<Movements> transactions){
        double sum = 0;     
        for(Movements m : transactions){
            if(m.getType()==Type.WITHDRAWAL){
                sum+= m.getAmount();
            }
        }
         return sum;        
    }
   /**
    * Make the final balance.
    * @param tblRegisters Data table to be traversed.
    * @return The final balance in String type.
    * The calculateFinalBalance function takes a JTable as input and looks up the final balance in the last cell of the last row of the table.
    * If the table has at least one row, the function extracts the value from the fifth column of the last row and returns it as a string.
    * If the table is empty, the final balance returned is null.
    */
   public String calculateFinalBalance(JTable tblRegisters){
       String finalBalance = null;
       if (tblRegisters.getRowCount()>0){
            for (int i = 0; i < tblRegisters.getRowCount(); i++) {
             String auxi = tblRegisters.getValueAt(i, 4).toString();            
                 finalBalance = auxi;
  
         }
        }
       return finalBalance;
   }
   /**
    * Make the initial balance.
    * @param month will be used as a reference point.
    * @param accountStatement It is used to obtain the list of movements.
    * @return initialBalance Returns the initial balance in double type.
    * The calculateInitialBalance function takes a month and an "AccountStatement" object as input.
    * Calculate and return the initial balance accumulated up to the specified month. 
    * The function iterates over the transactions in the statement and accumulates the deposit and
    * withdrawal amounts that correspond to the range of months up to the specified month.
    * The initial accumulated balance is calculated by adding the deposits and subtracting the withdrawals during that period.
    * The function returns this accumulated initial balance.
    */
   public Double calculateInitialBalance(int month, AccountStatement accountStatement){
       double initialBalance = 0;
       for(Movements m : accountStatement.getTransactions()){
           for(int i = 0; i<month; i++){
               if(m.getDate().getMonth()==i && m.getType() == Type.DEPOSIT){
                   initialBalance += m.getAmount();
               }else if(m.getDate().getMonth()==1 && m.getType() == Type.WITHDRAWAL){
                   initialBalance -= m.getAmount();
               }
           }
       }
       return initialBalance;
   } 
   /**
    * Organize a list per month.
    * @param month Selected month to display.
    * @param accountStatement It is used to obtain the list of movements.
    * @return Returns a List ordered by month.
    * The sortList function takes a month and an "AccountStatement" object as input. 
    * Filters transactions that belong to the specified month, sort by date and returns the resulting list.
    * The function iterates over the transactions on the statement, selects those that correspond to the given month,
    * adds them to a new list, and finally sorts that list by date before returning it.
    */
   public List<Movements> sortList(int month, AccountStatement accountStatement){
       List<Movements> transactions = new ArrayList<>();
                for(Movements m : accountStatement.getTransactions() ){
                     if(m.getDate().getMonth()==month){
                        transactions.add(m);
                    }
                }
                transactions.sort((m1,m2)->m1.getDate().compareTo(m2.getDate()));
                return transactions;
   }
   
}
