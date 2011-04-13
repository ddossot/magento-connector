/**
 * Mule Magento Cloud Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.magento.api.catalog.model;

import static org.junit.Assert.*;

import org.junit.Test;
/**Test for {@link MediaMimeType}*/
public class MediaMimeTypeUnitTest
{

    @Test
    public void testToString()
    {
        assertEquals("image/jpeg", MediaMimeType.JPEG.toString());
        assertEquals("image/gif", MediaMimeType.GIF.toString());
        assertEquals("image/png", MediaMimeType.PNG.toString());
    }

}


