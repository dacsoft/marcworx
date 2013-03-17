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
package org.talwood.marcworx.marc.containers;

import java.util.ArrayList;
import java.util.List;
import org.talwood.marcworx.marc.enums.FormatType;
import org.talwood.marcworx.marc.enums.MarcFileReadStatus;
import org.talwood.marcworx.exception.MarcException;
import org.talwood.marcworx.helpers.MarcWorxDataHelper;
import org.talwood.marcworx.helpers.MarcWorxObjectHelper;
import org.talwood.marcworx.marc.enums.RecordType;
import org.talwood.marcworx.marc.iface.BaseMarcRecord;

public class MarcRecord extends BaseMarcRecord {

  
    public MarcRecord(char recordType) throws MarcException {
        super(recordType);
    }


    
    public List<MarcTag> getAllTags(int[] tagNumbers) {
        List<MarcTag> results = new ArrayList<MarcTag>();
        for(MarcTag marcTag : getTags()) {
            if(MarcWorxObjectHelper.intInArray(tagNumbers, marcTag.getTagNumber())) {
                results.add(marcTag);
            }
        }
        return results;
    }

    public static MarcTag createFixedTag(int tagNumber, String tagData) throws MarcException {
        MarcTag result = new MarcTag(tagNumber, tagData);
        return result;
    }


    public static MarcTag createSubfieldTag(int tagNumber, char ind1, char ind2, List<MarcSubfield> subs) {
        MarcTag result = new MarcTag(tagNumber);
        result.setFirstIndicator(ind1);
        result.setSecondIndicator(ind2);
        result.addOrUpdateSubfields(subs);
        return result;
    }

    public static MarcTag createSubfieldTag(int tagNumber, char ind1, char ind2, MarcSubfield... subs) {
        MarcTag result = new MarcTag(tagNumber);
        result.setFirstIndicator(ind1);
        result.setSecondIndicator(ind2);
        for (int i = 0; i < subs.length; i++) {
            result.addOrUpdateSubfield(subs[i]);

        }
        return result;
    }

    public static MarcTag createSubfieldTag(int tagNumber, char ind1, char ind2, char subInd, String subData) {
        MarcTag result = new MarcTag(tagNumber);
        result.setFirstIndicator(ind1);
        result.setSecondIndicator(ind2);
        result.addOrUpdateSubfield(new MarcSubfield(subInd, subData));
        return result;
    }

    public void addOrUpdateTags(List<MarcTag> tags) {
        for (MarcTag marcTag : tags) {
            addOrUpdateTag(marcTag);
        }
    }

    public void addOrUpdateTag(MarcTag tag) {
        if(!tag.isIndexed()) {
            // New subfield, add it
            addNewTag(tag);
        } else {
            MarcTag newTag = findTagByIndex(tag.getTagIndex());
            if(newTag != null) {
                newTag.setTagNumber(tag.getTagNumber());
                newTag.setFirstIndicator(tag.getFirstIndicator());
                newTag.setSecondIndicator(tag.getSecondIndicator());
                newTag.replaceSubfieldListInternal(tag.getSubfields());
            } else {
                addNewTag(tag);
            }
        }
    }

    private void addNewTag(MarcTag tag) {
        MarcTag newTag = MarcWorxDataHelper.cloneTag(tag);
        nextTagIndex++;
        newTag.setTagIndex(nextTagIndex);
        tags.add(newTag);
    }

    public MarcTag findTagByIndex(int tagIndex) {
        MarcTag result = null;
        for (MarcTag marcTag : tags) {
            if(marcTag.getTagIndex() == tagIndex) {
                result = marcTag;
                break;
            }
        }
        return result;
    }

    public FormatType getFormatType() {
        return FormatType.findByFormatType(RecordType.findByRecordTypeCode(leader.getRecordType(), RecordType.RECORD_TYPE_UNKNOWN).getCode());
    }
}
