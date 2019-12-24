package com.example.binumtontine.activity;


import java.math.BigDecimal;
import java.util.Date;

//
// Data structure for invoices
//

public class InvoiceData {

    public int id;
    public int invoiceNumber;
    public Date invoiceDate;
    public String customerName;
    public String customerAddress;
    public BigDecimal invoiceAmount;
    public BigDecimal amountDue;


}
