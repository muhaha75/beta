/*
 * Copyright (C) 2014 GG-Net GmbH - Oliver Günther
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package beta.server.assist;

import beta.server.entity.Address;
import beta.server.entity.Contact;
import beta.server.entity.Sex;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StringWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * Generates useful names.
 *
 * @author oliver.guenther
 */
public class GeneratorFormFileSets {

    private List<String> namesFemaleFirst = new ArrayList<>();

    private List<String> namesMaleFirst = new ArrayList<>();

    private List<String> namesLast = new ArrayList<>();

    private List<String> streets = new ArrayList<>();

    private List<String> towns = new ArrayList<>();

    private final Random R;

    public GeneratorFormFileSets() throws RuntimeException {
        R = new Random();

        Map<String, List<String>> sources = new HashMap<>();
        sources.put("de_names_female_first.txt", namesFemaleFirst);
        sources.put("de_names_male_first.txt", namesMaleFirst);
        sources.put("de_names_last.txt", namesLast);
        sources.put("de_streets.txt", streets);
        sources.put("de_towns.txt", towns);

        for (String resource : sources.keySet()) {
            // load txt files.
            try (InputStream in = this.getClass().getResourceAsStream(resource)) {
                String all = read(in);
                List<String> data = sources.get(resource);
                for (StringTokenizer st = new StringTokenizer(all, "\n"); st.hasMoreTokens();) {
                    String s = st.nextToken();
                    s = s.replace("\r", ""); // safty net, if names file gets corrupted.
                    data.add(s);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void showSourceData(PrintStream out) {
        if (out == null) {
            return;
        }
        out.println("Names Female First:");
        namesFemaleFirst.forEach((string) -> {
            out.println(" " + string);
        });
        out.println("Names Male First:");
        namesMaleFirst.forEach((string) -> {
            out.println(" " + string);
        });
        out.println("Names Last:");
        namesLast.forEach((string) -> {
            out.println(" " + string);
        });
        out.println("Streets:");
        streets.forEach((string) -> {
            out.println(" " + string);
        });
        out.println("Towns:");
        towns.forEach((string) -> {
            out.println(" " + string);
        });
    }

    public Contact makeContact() {
        boolean female = R.nextBoolean();
        Sex gender = null;
        List<String> firstName = null;

        if (female) {
            gender = Sex.FEMALE;
            firstName = namesFemaleFirst;
        } else {
            gender = Sex.MALE;
            firstName = namesMaleFirst;
        }
        
        String title = null;
        if (R.nextInt(1000) % 3 == 0) {
            title = "Dr.";
        }

        return new Contact(gender,
                title,
                firstName.get(R.nextInt(firstName.size())),
                namesLast.get(R.nextInt(namesLast.size())));
    }

    public Address makeAddress() {        
        return new Address(streets.get(R.nextInt(streets.size())) +" "+ R.nextInt(300), 
                towns.get(R.nextInt(towns.size())) 
                , String.format("%05d", R.nextInt(100000)));       
    }

    // Copied from IOUtils
    private String read(InputStream input) throws IOException {
        final int EOF = -1;
        final int DEFAULT_BUFFER_SIZE = 1024 * 4;

        try (final StringWriter sw = new StringWriter();
                final InputStreamReader in = new InputStreamReader(input, "UTF-8")) {
            long count = 0;
            int n;
            char[] buffer = new char[DEFAULT_BUFFER_SIZE];
            while (EOF != (n = in.read(buffer))) {
                sw.write(buffer, 0, n);
                count += n;
            }
            return sw.toString();
        }
    }
}
