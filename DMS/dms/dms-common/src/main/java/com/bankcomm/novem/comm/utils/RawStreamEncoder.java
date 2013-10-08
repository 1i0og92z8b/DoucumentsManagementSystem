/**
 * 
 */
package com.bankcomm.novem.comm.utils;

import java.util.Map;

import com.bocom.jump.bp.JumpException;
import com.bocom.jump.bp.channel.http.interceptors.JsonEncoder;

/**
 * @author <潘素春 pansc@bankcomm.com> 交通银行 2011-4-8
 * 
 */
public class RawStreamEncoder extends JsonEncoder {

	/**
	 * Context中字节的名字为“BYTES”
	 */
	@Override
	public Object transform(final Object in, final String id)
			throws JumpException {
		byte[] bytes = (byte[]) ((Map<?, ?>) in).get("BYTES");
		if (bytes == null) {
			bytes = new byte[0];
		}
		final String encoding = (String) ((Map<?, ?>) in).get("ENCODING");
		if (encoding != null && !"".equals(encoding)) {
			setEncoding(encoding);
		}

		return bytes;
	}
}
