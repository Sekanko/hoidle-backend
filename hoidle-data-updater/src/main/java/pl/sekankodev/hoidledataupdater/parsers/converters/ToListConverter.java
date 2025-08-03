package pl.sekankodev.hoidledataupdater.parsers.converters;

import com.opencsv.bean.AbstractBeanField;

import java.util.Arrays;
import java.util.List;

public class ToListConverter extends AbstractBeanField<List<String>, String> {
    @Override
    protected List<String> convert(String s) {
        String regex = "\\s*,\\s*";
        return Arrays.asList(s.split(regex));
    }
}
