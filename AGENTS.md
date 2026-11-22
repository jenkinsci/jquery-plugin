# AGENTS.md — Jenkins jQuery Plugin (`jquery`)

## Overview
- **Plugin ID**: `jquery`
- **Repository**: `jenkinsci/jquery-plugin`
- **Java Package**: `hudson.plugins.jquery`
- **Jenkins Baseline**: `2.479.3` (`org.jenkins-ci.plugins:plugin:6.x`)

Provides the `org.kohsuke.stapler:stapler-adjunct-jquery` (`1.12.4-0`) library via a global `PageDecorator` (`hudson.plugins.jquery.JQuery`), binds `var Q = jQuery.noConflict();` to avoid conflicts with legacy `$` globals, and provides an optional `hudson.plugins.jquery.colorbox` Stapler adjunct.

---

## Build, Test & Verification Commands

### Prerequisites
- **JDK**: Java 17, 21, or 25
- **Maven**: Apache Maven `3.9+`

### Core Maven Commands
```bash
# Full build, JUnit 5 tests, InjectedTest, SpotBugs, and Enforcer checks
mvn clean verify

# Run unit tests quickly (skipping InjectedTest during rapid iteration)
mvn test -Dtest=!InjectedTest

# Run JQueryTest directly
mvn test -Dtest=JQueryTest

# Run SpotBugs and Maven Enforcer checks directly
mvn spotbugs:check enforcer:enforce
```

---

## Architecture & Packaging Guardrails

1. **Wrapped Component Versioning (`1.12.4-${changelist}`)**:
   - `pom.xml` uses `<version>${revision}-${changelist}</version>` with `<revision>1.12.4</revision>` and `<changelist>999999-SNAPSHOT</changelist>` to preserve the wrapped jQuery `1.12.4` version prefix in JEP-229 Continuous Delivery releases.
2. **Strict Bundled Artifacts (`hpi.strictBundledArtifacts`)**:
   - `pom.xml` sets `<hpi.bundledArtifacts>stapler-adjunct-jquery</hpi.bundledArtifacts>` and `<hpi.strictBundledArtifacts>true</hpi.strictBundledArtifacts>`.
   - Any additional runtime/compile dependencies added to `<dependencies>` will fail the build unless explicitly bundled or marked `<scope>test</scope>` / `<scope>provided</scope>`.
3. **JUnit 5 Exclusively**:
   - Write all tests with JUnit 5 (`org.junit.jupiter.api.*`) and `@WithJenkins` (`org.jvnet.hudson.test.junit.jupiter.WithJenkins`).
4. **Adjunct Paths**:
   - Global page header decorator: `src/main/resources/hudson/plugins/jquery/JQuery/header.jelly`
   - `noConflict` binding (`var Q = jQuery.noConflict();`): `src/main/resources/hudson/plugins/jquery/JQuery/jquery-no-conflict.js`
   - Optional ColorBox adjunct: `src/main/resources/hudson/plugins/jquery/colorbox.jelly`
