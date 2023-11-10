/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.itson.catrina.enums;

import com.google.gson.annotations.SerializedName;

/**
 * Enumerator to determine if it is Deposit or Withdrawal.
 * @author José Alvarez 
 */
public enum Type {
    @SerializedName ("1")
    DEPOSIT,
    @SerializedName ("2")
    WITHDRAWAL
}
