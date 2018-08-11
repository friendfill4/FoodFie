package com.friendfill.foodifie;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.friendfill.foodifie.model.Bill;
import com.friendfill.foodifie.model.Category;
import com.friendfill.foodifie.model.Customer;
import com.friendfill.foodifie.model.Item;
import com.friendfill.foodifie.model.Staff;
import com.friendfill.foodifie.model.Table;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by FriendFill on 10-Jan-18.
 */

public class ApplicationConfig extends Application {
    public static ArrayList<Table> tables;
    public static ArrayList<Customer> customers;
    public static ArrayList<Item> items;
    public static ArrayList<Category> categories;
    public static ArrayList<Category> home_categories;
    public static ArrayList<Bill> bills;
    public static ArrayList<Staff> staff;
    public static ProgressDialog progressDialog;

    public static void initStaff() {
        staff = new ArrayList<>();
        staff.add(new Staff(1, "Jay Acharya", "Owner", "08:00 - 23:00", "acharyajay007@gmail.com", "9978078494", true, "http://keenthemes.com/preview/metronic/theme/assets/pages/media/users/avatar1.jpg"));
        staff.add(new Staff(2, "Ram Paramar", "Chef", "10:00 - 23:00", "ramparmar28995s@gmail.com", "9586434456", false, "http://keenthemes.com/preview/metronic/theme/assets/pages/media/users/avatar4.jpg"));
        staff.add(new Staff(3, "Chintan Takwani", "Chef", "20:00 - 23:00", "chintantakwani.gec@gmail.com", "9427760546", false, "http://keenthemes.com/preview/metronic/theme/assets/pages/media/users/avatar3.jpg"));
        staff.add(new Staff(4, "Hardik Gadesha", "Waiter", "07:00 - 23:00", "hardik738@gmail.com", "9428710501", false, "http://keenthemes.com/preview/metronic/theme/assets/pages/media/users/avatar7.jpg"));

    }

    public static void initTables() {
        tables = new ArrayList<>();
        tables.add(new Table(1, "Table 1", "", 2, 2, 0));
        tables.add(new Table(2, "Table 2", "", 4, 3, 0));
        tables.add(new Table(3, "Table 3", "", 5, 4, 1));
        tables.add(new Table(4, "Table 4", "", 10, 4, 0));
        tables.add(new Table(5, "Table 5", "", 4, 4, 0));
        tables.add(new Table(6, "Table 6", "", 2, 2, 0));
        tables.add(new Table(7, "Table 7", "", 5, 4, 0));
    }

    public static void initCustomers() {
        customers = new ArrayList<>();
        customers.add(new Customer(1, "Jay Acharya", "9978078494", "acharyajay007@gmail.com", "402,Ratnam Elegance", new Date()));
        customers.add(new Customer(2, "Ram Parmar", "9978078494", "acharyajay007@gmail.com", "402,Ratnam Elegance", new Date()));
        customers.add(new Customer(3, "Chintan Takwani", "9978078494", "acharyajay007@gmail.com", "402,Ratnam Elegance", new Date()));
        customers.add(new Customer(4, "Hardik Gadesha", "9978078494", "acharyajay007@gmail.com", "402,Ratnam Elegance", new Date()));

    }

    public static void initItems() {
        items = new ArrayList<>();
        items.add(new Item(1, "Panner Tikka", "<b>Pizza</b> is a traditional <a href=\"/wiki/Italian_cuisine\" title=\"Italian cuisine\">Italian</a> <a href=\"/wiki/Dish_(food)\" title=\"Dish (food)\">dish</a> consisting of a yeasted <a href=\"/wiki/Flatbread\" title=\"Flatbread\">flatbread</a> typically topped with <a href=\"/wiki/Tomato_sauce\" title=\"Tomato sauce\">tomato sauce</a> and <a href=\"/wiki/Cheese\" title=\"Cheese\">cheese</a> and baked in an oven. It can also be topped with additional vegetables, meats, and <a href=\"/wiki/Condiments\" class=\"mw-redirect\" title=\"Condiments\">condiments</a>, and can be made without cheese.</p>\n" +
                "<p>The term <i>pizza</i> was first recorded in the 10th century, in a Latin manuscript from the <a href=\"/wiki/Southern_Italy\" title=\"Southern Italy\">Southern Italy</a> town of <a href=\"/wiki/Gaeta\" title=\"Gaeta\">Gaeta</a> in <a href=\"/wiki/Lazio\" title=\"Lazio\">Lazio</a>, on the border with Campania.<sup id=\"cite_ref-MartinMaiden_1-0\" class=\"reference\"><a href=\"#cite_note-MartinMaiden-1\">[1]</a></sup> Modern pizza was invented in <a href=\"/wiki/Naples\" title=\"Naples\">Naples</a>, and the dish and its variants have since become popular and common in many areas of the world.<sup id=\"cite_ref-Miller_2-0\" class=\"reference\"><a href=\"#cite_note-Miller-2\">[2]</a></sup> In 2009, upon <a href=\"/wiki/Italy\" title=\"Italy\">Italy</a>'s request, <a href=\"/wiki/Neapolitan_pizza\" title=\"Neapolitan pizza\">Neapolitan pizza</a> was registered with the <a href=\"/wiki/European_Union\" title=\"European Union\">European Union</a> as a <a href=\"/wiki/Traditional_Speciality_Guaranteed\" class=\"mw-redirect\" title=\"Traditional Speciality Guaranteed\">Traditional Speciality Guaranteed</a> dish.<sup id=\"cite_ref-3\" class=\"reference\"><a href=\"#cite_note-3\">[3]</a></sup><sup id=\"cite_ref-4\" class=\"reference\"><a href=\"#cite_note-4\">[4]</a></sup> <i>Associazione Verace Pizza Napoletana</i> (True Neapolitan Pizza Association), a non-profit organization founded in 1984 with headquarters in Naples, aims to \"promote and protect... the true Neapolitan pizza\".<sup id=\"cite_ref-AVPN_5-0\" class=\"reference\"><a href=\"#cite_note-AVPN-5\">[5]</a>", "Punjabi", "7 Adults + 2 Kids", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcShnOP4dwczL8D5nTjgPHc13kA6evwmTkeA5Iv85l5SIQOmg4-EGA", 3.00, 120.00));
        items.add(new Item(2, "Danhi Handi", "Lorem Ipsum Pasrunc", "Punjabi", "5 Adults ", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT4vn7O-FTSdlBQlReuCyDBqDdRtf3ZuxL8ipKKVFAGrEvfW3lN", 4.00, 150.00));
        items.add(new Item(3, "Italian Pizza", "Lorem Ipsum Pasrunc", "Pizza", "2 Adults", "http://www.baltana.com/files/wallpapers-2/Food-HD-Images-04860.jpg", 5, 240.00));
        items.add(new Item(4, "Veg.Momoes", "Lorem Ipsum Pasrunc", "Momoes", "4 Adults", "http://images.all-free-download.com/images/graphiclarge/food_picture_01_hd_pictures_167558.jpg", 1, 290.00));
    }

    public static void initHomeCategory() {
        initItems();
        home_categories = new ArrayList<>();
        home_categories.add(new Category(1, "Punjabi Dines", "", items));
        home_categories.add(new Category(2, "Delhi Chats", "", items));
        home_categories.add(new Category(3, "Madrasi Masti", "", items));
        home_categories.add(new Category(4, "South Indian", "", items));
        home_categories.add(new Category(5, "Bangali Sweets", "", items));
    }

    public static void initItemCategory() {
        categories = new ArrayList<>();
        categories.add(new Category(1, "Punjabi Dines", "", items));
        categories.add(new Category(2, "Delhi Chats", "", items));
        categories.add(new Category(3, "Madrasi Masti", "", items));
        categories.add(new Category(4, "South Indian", "", items));
        categories.add(new Category(5, "Bangali Sweets", "", items));
    }

    public static void initBills() {
        bills = new ArrayList<>();
        initCustomers();
        initTables();
        initItems();
        bills.add(new Bill(1, "1", new Date(), ApplicationConfig.customers.get(1), ApplicationConfig.tables.get(1), 500.00, ApplicationConfig.items));
        bills.add(new Bill(2, "2", new Date(), ApplicationConfig.customers.get(2), ApplicationConfig.tables.get(2), 1500.00, ApplicationConfig.items));
        bills.add(new Bill(3, "3", new Date(), ApplicationConfig.customers.get(3), ApplicationConfig.tables.get(3), 180.00, ApplicationConfig.items));
        bills.add(new Bill(4, "4", new Date(), ApplicationConfig.customers.get(0), ApplicationConfig.tables.get(0), 250.00, ApplicationConfig.items));
    }

    public static void setProgressDialog(Context context, String title, String message) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
    }

    public static Date StringToDate(String s) throws ParseException {
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(s);
        return date;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/gothic.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
       /* initTables();
        initCustomers();
        initItems();
        initHomeCategory();
        initItemCategory();*/
    }
}
