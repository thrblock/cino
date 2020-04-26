package com.thrblock.cino.annotation.proc;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.script.Bindings;
import javax.script.ScriptEngine;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.thrblock.cino.annotation.ECMAComponent;
import com.thrblock.cino.annotation.EnableECMAScript;
import com.thrblock.cino.component.CinoComponent;

@Component
public class ECMAAnnotationProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(ECMAAnnotationProcessor.class);

    @Autowired
    private ScriptEngine engine;

    public void processByECMA(Object o, EnableECMAScript anno) {
        Bindings param = engine.createBindings();
        param.put("log", LOG);
        param.put("tgt", o);
        String resourceName = StringUtils.isEmpty(anno.value()) ? (o.getClass().getSimpleName() + ".js") : anno.value();
        Resource resource = new ClassPathResource(resourceName);
        try {
            engine.eval(resource2Str(resource), param);
        } catch (Exception e) {
            LOG.error("exception in eval script:{}", e);
        }
    }

    public void processComponentECMA(CinoComponent comp, ECMAComponent ecma) {
        String resourceName = StringUtils.isEmpty(ecma.value()) ? (comp.getClass().getSimpleName() + ".js")
                : ecma.value();
        Resource r = new ClassPathResource(resourceName);
        comp.setScriptResouce(r);
    }

    private String resource2Str(Resource r) {
        try (InputStream is = r.getInputStream()) {
            return IOUtils.toString(r.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOG.error("IOException in resource2Str", e);
        }
        return "";
    }
}
