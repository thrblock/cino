package com.thrblock.game.demo.component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.thrblock.cino.component.CinoComponent;

@Component
public class CityVi extends CinoComponent {

    public void init() {
        autoShowHide();
        Resource resource = new ClassPathResource("citys.json");
        try (InputStream is = resource.getInputStream()) {
            List<City> citys = JSONObject.parseArray(IOUtils.toString(is, StandardCharsets.UTF_8), City.class);
            citys.stream().filter(c -> c.getCityCode().equals("330400")).findAny().ifPresent(j -> {
                String border = j.getPositionBorder();
                String[] points = border.split(";");
                Arrays.stream(points).forEach(dp -> {
                    String[] ps = dp.split(",");
                    Double lon = Double.valueOf(ps[0]);
                    Double lat = Double.valueOf(ps[1]);
                    shapeFactory.buildGLRect((int)(lon * 1000000),(int)(lat * 1000000),1000000,1000000);
                });
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class City {
        @JSONField(name = "city_code")
        String cityCode;
        @JSONField(name = "city_name")
        String cityName;
        @JSONField(name = "position_border")
        String positionBorder;

        public String getCityCode() {
            return cityCode;
        }

        public void setCityCode(String cityCode) {
            this.cityCode = cityCode;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getPositionBorder() {
            return positionBorder;
        }

        public void setPositionBorder(String positionBorder) {
            this.positionBorder = positionBorder;
        }
    }
}
