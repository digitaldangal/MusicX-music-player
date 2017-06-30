package com.rks.musicx.misc.utils;

/*
 * ©2017 Rajneesh Singh
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Coolalien on 5/24/2017.
 */
public class FileTarget extends SimpleTarget<Bitmap> {

    String fileName;

    public FileTarget(String fileName, int width, int height) {
        super(width, height);
        this.fileName = fileName;
    }


    @Override
    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
        new AsyncTask<Void, Void, Void>() {
            FileOutputStream outputStream = null;
            @Override
            protected Void doInBackground(Void... voids) {
                if (resource != null && !resource.isRecycled()) {
                    try {
                        outputStream = new FileOutputStream(fileName);
                        resource.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                        outputStream.flush();
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        try {
                            if (outputStream != null){
                                outputStream.close();
                                outputStream = null;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                try {
                    if (outputStream != null) {
                        outputStream.close();
                        outputStream = null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }
}
