/*
 * Copyright (C) 2012 Andrew Neal Licensed under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package com.andrew.music.loaders;

import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.ArtistColumns;

import com.andrew.music.R;
import com.andrew.music.model.Artist;
import com.andrew.music.utils.Lists;
import com.andrew.music.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to query {@link MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI} and
 * return the artists on a user's device.
 * 
 * @author Andrew Neal (andrewdneal@gmail.com)
 */
public class ArtistLoader extends WrappedAsyncTaskLoader<List<Artist>> {

    /**
     * The result
     */
    private final ArrayList<Artist> mArtistsList = Lists.newArrayList();

    /**
     * The {@link Cursor} used to run the query.
     */
    private Cursor mCursor;

    /**
     * Constructor of <code>ArtistLoader</code>
     * 
     * @param context The {@link Context} to use
     */
    public ArtistLoader(final Context context) {
        super(context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Artist> loadInBackground() {
        // Create the Cursor
        mCursor = makeArtistCursor(getContext());
        // Gather the data
        if (mCursor != null && mCursor.moveToFirst()) {
            do {
                // Copy the artist id
                final long id = mCursor.getLong(0);

                // Copy the artist name
                final String artistName = mCursor.getString(1);

                // Copy the number of albums
                final int albumCount = mCursor.getInt(2);

                // Copy the number of songs
                final int songCount = mCursor.getInt(3);

                // Create a new artist
                final Artist artist = new Artist(id, artistName, songCount, albumCount);

                // Add everything up
                mArtistsList.add(artist);
            } while (mCursor.moveToNext());
        }
        // Close the cursor
        if (mCursor != null) {
            mCursor.close();
            mCursor = null;
        }
        return mArtistsList;
    }

    /**
     * Creates the {@link Cursor} used to run the query.
     * 
     * @param context The {@link Context} to use.
     * @return The {@link Cursor} used to run the artist query.
     */
    public static final Cursor makeArtistCursor(final Context context) {
        return context.getContentResolver().query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
                new String[] {
                        /* 0 */
                        BaseColumns._ID,
                        /* 1 */
                        ArtistColumns.ARTIST,
                        /* 2 */
                        ArtistColumns.NUMBER_OF_ALBUMS,
                        /* 3 */
                        ArtistColumns.NUMBER_OF_TRACKS
                }, null, null, PreferenceUtils.getInstance(context).getArtistSortOrder());
    }
}
