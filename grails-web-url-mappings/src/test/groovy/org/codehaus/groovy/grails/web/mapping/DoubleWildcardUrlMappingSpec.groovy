/*
 * Copyright 2012 the original author or authors.
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

package org.codehaus.groovy.grails.web.mapping

import org.springframework.core.io.ByteArrayResource

/**
 */
class DoubleWildcardUrlMappingSpec extends AbstractUrlMappingsSpec{

    void testDoubleWildcardInParam() {

        given:
            def urlMappingsHolder = getUrlMappingsHolder {
                "/components/image/$path**?" {
                    controller = "components"
                    action = "image"
                }
                "/stuff/image/$path**" {
                    controller = "components"
                    action = "image"
                }

                "/cow/$controller/$action?/$id?/$path**?"()
            }

        when:
            def infos = urlMappingsHolder.matchAll("/cow/wiki/show/2/doc/?d=1")

        then:
            infos
            infos[0].parameters
    }
}
