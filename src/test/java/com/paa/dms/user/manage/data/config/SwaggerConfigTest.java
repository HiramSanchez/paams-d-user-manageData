/**
 * Copyright (c) ${package.getClass().forName("java.time.LocalDateTime").getMethod("now").invoke(null).format($package.Class.forName("java.time.format.DateTimeFormatter").getMethod("ofPattern", $package.Class).invoke(null, "yyyy"))} Bancoppel
 *
 * <p>Licensed under the GNU General Public License, Version 3 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>https://www.gnu.org/licenses/gpl-3.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.paa.dms.user.manage.data.config;
import com.paa.dms.user.manage.data.constants.APIConstants;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SwaggerConfigTest {

  @InjectMocks
  private SwaggerConfig swaggerConfig;
  @Mock
  private APIConstants apiConstants;

  @Test
  void productApiTest() {
    Mockito.when(apiConstants.getSERVICE_NAME()).thenReturn(StringUtils.EMPTY);

    swaggerConfig.productApi();

    Mockito.verify(apiConstants, Mockito.times(1)).getSERVICE_NAME();
  }
}
