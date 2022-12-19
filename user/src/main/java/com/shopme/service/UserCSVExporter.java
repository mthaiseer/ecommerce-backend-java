package com.shopme.service;

import com.shopme.entity.User;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserCSVExporter {

    public void export(HttpServletResponse httpResponse, List<User> users) throws IOException {


        ICsvBeanWriter csvWriter = null;
        try{

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            String fileName  =  dateFormat.format(new Date());
            String file = "users_"+ fileName+ ".csv";
            httpResponse.setContentType("text/csv");
            httpResponse.setHeader("Content-Disposition", "attachment; filename = "+ file);
            csvWriter =  new CsvBeanWriter(httpResponse.getWriter(),
                    CsvPreference.STANDARD_PREFERENCE);

            String csvHeader [] =  {"User ID", "Email", "First Name", "Last Name", "Roles", "Enabled"};
            String fieldMapping []  = {"id", "email", "firstName", "lastName", "roles", "enabled"};
            csvWriter.writeHeader(csvHeader);

            for(User user: users){
                csvWriter.write(user, fieldMapping);
            }

        }finally {
            if(csvWriter != null) csvWriter.close();

        }
    }
}
