/*
 * Copyright (c) 2012, the Last.fm Java Project and Committers All rights
 * reserved. Redistribution and use of this software in source and binary forms,
 * with or without modification, are permitted provided that the following
 * conditions are met: - Redistributions of source code must retain the above
 * copyright notice, this list of conditions and the following disclaimer. -
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution. THIS SOFTWARE IS
 * PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO
 * EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.andrew.music.lastfm;

import java.util.Collection;
import java.util.Iterator;

/**
 * A <code>PaginatedResult</code> is returned by methods which result set might
 * be so large that it needs to be paginated. Each <code>PaginatedResult</code>
 * contains the total number of result pages, the current page and a
 * <code>Collection</code> of entries for the current page.
 * 
 * @author Janni Kovacs
 */
public class PaginatedResult<T> implements Iterable<T> {

    private final int page;

    private final int totalPages;

    public final Collection<T> pageResults;

    /**
     * @param page
     * @param totalPages
     * @param pageResults
     */
    PaginatedResult(final int page, final int totalPages, final Collection<T> pageResults) {
        this.page = page;
        this.totalPages = totalPages;
        this.pageResults = pageResults;
    }

    /**
     * Returns the page number of this result.
     * 
     * @return page number
     */
    public int getPage() {
        return page;
    }

    /**
     * Returns the total number of pages available.
     * 
     * @return total pages
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * Returns <code>true</code> if this Result contains no elements, which is
     * the case for service calls that would have returned a
     * <code>PaginatedResult</code> but fail.
     * 
     * @return <code>true</code> if this result contains no elements
     */
    public boolean isEmpty() {
        return pageResults == null || pageResults.isEmpty();
    }

    @Override
    public Iterator<T> iterator() {
        return pageResults.iterator();
    }
}
