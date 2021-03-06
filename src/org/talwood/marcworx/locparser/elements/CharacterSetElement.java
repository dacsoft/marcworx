/* MarcWorx MARC Library - Utilities for manipulation of MARC records
    Copyright (C) 2013  Todd Walker, Talwood Solutions

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package org.talwood.marcworx.locparser.elements;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CharacterSetElement {
    
    private String name;
    private String isoCode;
    private List<CodeElement> codeList = new ArrayList<CodeElement>();
    private List<GroupingElement> groupingList = new ArrayList<GroupingElement>();
    
    public CharacterSetElement() {
        
    }

    @XmlAttribute(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute(name="ISOcode")
    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }
    
    @XmlElement(name="code")
    public List<CodeElement> getCodeList() {
        return codeList;
    }

    public void setCodeList(List<CodeElement> codeList) {
        this.codeList = codeList;
    }

    @XmlElement(name="grouping")
    public List<GroupingElement> getGroupingList() {
        return groupingList;
    }

    public void setGroupingList(List<GroupingElement> groupingList) {
        this.groupingList = groupingList;
    }
    
    
}
