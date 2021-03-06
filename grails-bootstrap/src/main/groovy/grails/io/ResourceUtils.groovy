/*
 * Copyright 2014 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package grails.io

import grails.util.BuildSettings
import groovy.transform.CompileStatic
import org.grails.io.support.GrailsResourceUtils


/**
 * Utility methods for interacting with resources
 *
 * @author Graeme Rocher
 * @since 3.0
 */
@CompileStatic
class ResourceUtils extends GrailsResourceUtils {

    /**
     * Obtains the package names for the project. Works at development time only, do not use at runtime.
     *
     * @return The project package names
     */
    static Iterable<String> getProjectPackageNames() {
        return getProjectPackageNames(BuildSettings.BASE_DIR)
    }

    static Iterable<String> getProjectPackageNames(File baseDir) {
        File rootDir = baseDir ? new File(baseDir, "grails-app") : null
        Set<String> packageNames = []
        if (rootDir?.exists()) {
            rootDir.eachDir { File dir ->
                def dirName = dir.name
                if (!dir.hidden && !dirName.startsWith('.') && !['conf', 'i18n', 'assets', 'views'].contains(dirName)) {
                    populatePackages(dir, packageNames, "")
                }
            }
        }

        return packageNames
    }

    protected static populatePackages(File rootDir, Collection<String> packageNames, String prefix) {
        rootDir.eachDir { File dir ->
            def dirName = dir.name
            if (!dir.hidden && !dirName.startsWith('.')) {
                packageNames << "${prefix}${dirName}".toString()

                populatePackages(dir, packageNames, "${prefix}${dirName}.")
            }
        }
    }
}
