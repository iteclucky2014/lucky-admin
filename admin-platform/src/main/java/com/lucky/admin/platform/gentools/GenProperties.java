package com.lucky.admin.platform.gentools;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "gen")
public class GenProperties {

    private String bar;
    private String genPath;
    private List<DomainConf> domains;
    private List<RepoConf> repositorys;
    private List<CtrlConf> controllers;

    public GenProperties.DomainConf findDomian(String domainName) {
        for (DomainConf domainConf : domains) {
            if (domainConf.getName().equals(domainName)) {
                return domainConf;
            }
        }
        return null;
    }

    public String getBar() {
        return bar;
    }

    public void setBar(String bar) {
        this.bar = bar;
    }

    public List<DomainConf> getDomains() {
        return domains;
    }

    public void setDomains(List<DomainConf> domains) {
        this.domains = domains;
    }

    public List<RepoConf> getRepositorys() {
        return repositorys;
    }

    public void setRepositorys(List<RepoConf> repositorys) {
        this.repositorys = repositorys;
    }

    public List<CtrlConf> getControllers() {
        return controllers;
    }

    public void setControllers(List<CtrlConf> controllers) {
        this.controllers = controllers;
    }

    public String getGenPath() {
        return genPath;
    }

    public void setGenPath(String genPath) {
        this.genPath = genPath;
    }

    public static class DomainConf {
        private String name;
        private List<String> fields;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getFields() {
            return fields;
        }

        public void setFields(List<String> fields) {
            this.fields = fields;
        }
    }
    public static class RepoConf {
        private String name;
        private String domain;
        private String pkType;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public String getPkType() {
            return pkType;
        }

        public void setPkType(String pkType) {
            this.pkType = pkType;
        }
    }
    public static class CtrlConf {
        private String name;
        private String domain;
        private String repository;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public String getRepository() {
            return repository;
        }

        public void setRepository(String repository) {
            this.repository = repository;
        }
    }
}
