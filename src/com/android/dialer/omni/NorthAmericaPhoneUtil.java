/*
 *  Copyright (C) 2014 The OmniROM Project
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.android.dialer.omni;

import com.android.dialer.omni.PlaceUtil;

public class NorthAmericaPhoneUtil {
    private final static String QUERY_AREACODE_URL = "http://www.area-codes.com/exchange/exchange.asp?npa="
    private final static String QUERY_PREFIX_URL = "&nxx=";
    private final static String COUNTRY_REGEX = "<a.*?data-gaaction=\"associated_0\".*?>(.*?)<.*?</a>";
    private final static Set<String> ALLOWED_USA_STATES = null;
    private final static Set<String> ALLOWED_CAN_STATES = null;
    
    
    public boolean isUsaPhoneNumber(Phonenumber phoneNumber) {
        return isNorthAmericanPhoneNumber(phoneNumber, ALLOWED_USA_STATES);
    }
    
    public boolean isCanadianPhoneNumber(Phonenumber phoneNumber) {
        return isNorthAmericanPhoneNumber(phoneNumber, ALLOWED_CAN_STATES);
    }
    
    public boolean isNorthAmericanPhoneNumber(Phonenumber phoneNumber, Set<String> allowedStateCodes) {
        boolean result = false;
        
        try {
            String html = new String(PlaceUtil.getRequest(QUERY_AREACODE_URL + phoneNumber + QUERY_PREFIX_URL + "XXX"));

            Pattern regex = Pattern.compile(COUNTRY_REGEX, Pattern.DOTALL);
            Matcher matcher = regex.matcher(html);

            if (matcher.find()) {
                if (allowedStateCodes.contains(matcher.group(1).trim()) {
                    result = true;
                }

            } else {
                Log.w(TAG, "Regex matched nothing!");
            }
        } catch (Exception e) {
            Log.e(TAG, "Unable to do reverse lookup", e);
        }
        
        return result;
    }
}
