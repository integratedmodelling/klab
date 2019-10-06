package org.integratedmodelling.controlcenter.runtime;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.function.BiConsumer;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.CountingOutputStream;

public class Downloader {

	private long totalLength;
	private BiConsumer<Long, Long> handler;
	private URL url;
	private File file;

	public Downloader(URL url, File file, BiConsumer<Long, Long> handler) {
		this.url = url;
		this.file = file;
		this.handler = handler;
	}
	
	private class ProgressListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			handler.accept(((DownloadCountingOutputStream) e.getSource()).getByteCount(), totalLength);
		}
	}

	/**
	 * Start a download thread and return.
	 */
	public void startDownload() {
		new Thread() {
            @Override
            public void run() {
                download();
            }
		}.start();
	}
	
	/**
	 * Start downloading and block until success or failure.
	 */
	public void download() {

		ProgressListener progressListener = new ProgressListener();
		try (OutputStream os = new FileOutputStream(file); InputStream is = url.openStream()) {
			DownloadCountingOutputStream dcount = new DownloadCountingOutputStream(os);
			dcount.setListener(progressListener);
			this.totalLength = Integer.parseInt(url.openConnection().getHeaderField("Content-Length"));
			IOUtils.copy(is, dcount);
			finish();
		} catch (Exception e) {
			fail(e);
		}
	}

	protected void finish() {
	}

	protected void fail(Exception e) {
	}

	class DownloadCountingOutputStream extends CountingOutputStream {

		private ActionListener listener = null;

		public DownloadCountingOutputStream(OutputStream out) {
			super(out);
		}

		public void setListener(ActionListener listener) {
			this.listener = listener;
		}

		@Override
		protected void afterWrite(int n) throws IOException {
			super.afterWrite(n);
			if (listener != null) {
				listener.actionPerformed(new ActionEvent(this, 0, null));
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		URL url = new URL("http://www.integratedmodelling.org/downloads/IALE15_ehabitat_JML.pdf");
		File file = new File(System.getProperty("user.home") + File.separator + "dio.pdf");
		Downloader downloader = new Downloader(url, file, (sofar, total) -> System.out.println("Downloaded " + sofar + "/" + total));
		downloader.download();
	}

}
