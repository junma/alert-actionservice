/*******************************************************************************
 * Copyright 2012 FZI-HIWI
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package de.fzi.ALERT.actor.SubscriptionEditor.Form;

public class Preferences {
	
    private String[] subChBx;

    private String subRadio;
    
    public String[] getsubChBx() {
        return subChBx;
    }

    public void setsubChBx(String[] subChBx) {
        this.subChBx = subChBx;
    }

    public String getsubRadio() {
        return subRadio;
    }

    public void setsubRadio(String subRadio) {
        this.subRadio= subRadio;
    }
    
}
