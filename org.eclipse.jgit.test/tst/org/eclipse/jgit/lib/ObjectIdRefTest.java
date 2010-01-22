/*
 * Copyright (C) 2010, Google Inc.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 *
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above
 *   copyright notice, this list of conditions and the following
 *   disclaimer in the documentation and/or other materials provided
 *   with the distribution.
 *
 * - Neither the name of the Eclipse Foundation, Inc. nor the
 *   names of its contributors may be used to endorse or promote
 *   products derived from this software without specific prior
 *   written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND
 * CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.eclipse.jgit.lib;

import junit.framework.TestCase;

public class ObjectIdRefTest extends TestCase {
	private static final ObjectId ID_A = ObjectId
			.fromString("41eb0d88f833b558bddeb269b7ab77399cdf98ed");

	private static final ObjectId ID_B = ObjectId
			.fromString("698dd0b8d0c299f080559a1cffc7fe029479a408");

	private static final String name = "refs/heads/a.test.ref";

	public void testConstructor_PeeledStatusNotKnown() {
		ObjectIdRef r;

		r = new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE, name, ID_A);
		assertSame(Ref.Storage.LOOSE, r.getStorage());
		assertSame(name, r.getName());
		assertSame(ID_A, r.getObjectId());
		assertFalse("not peeled", r.isPeeled());
		assertNull("no peel id", r.getPeeledObjectId());
		assertSame("leaf is this", r, r.getLeaf());
		assertSame("target is this", r, r.getTarget());
		assertFalse("not symbolic", r.isSymbolic());

		r = new ObjectIdRef.Unpeeled(Ref.Storage.PACKED, name, ID_A);
		assertSame(Ref.Storage.PACKED, r.getStorage());

		r = new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE_PACKED, name, ID_A);
		assertSame(Ref.Storage.LOOSE_PACKED, r.getStorage());

		r = new ObjectIdRef.Unpeeled(Ref.Storage.NEW, name, null);
		assertSame(Ref.Storage.NEW, r.getStorage());
		assertSame(name, r.getName());
		assertNull("no id on new ref", r.getObjectId());
		assertFalse("not peeled", r.isPeeled());
		assertNull("no peel id", r.getPeeledObjectId());
		assertSame("leaf is this", r, r.getLeaf());
		assertSame("target is this", r, r.getTarget());
		assertFalse("not symbolic", r.isSymbolic());
	}

	public void testConstructor_Peeled() {
		ObjectIdRef r;

		r = new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE, name, ID_A);
		assertSame(Ref.Storage.LOOSE, r.getStorage());
		assertSame(name, r.getName());
		assertSame(ID_A, r.getObjectId());
		assertFalse("not peeled", r.isPeeled());
		assertNull("no peel id", r.getPeeledObjectId());
		assertSame("leaf is this", r, r.getLeaf());
		assertSame("target is this", r, r.getTarget());
		assertFalse("not symbolic", r.isSymbolic());

		r = new ObjectIdRef.PeeledNonTag(Ref.Storage.LOOSE, name, ID_A);
		assertTrue("is peeled", r.isPeeled());
		assertNull("no peel id", r.getPeeledObjectId());

		r = new ObjectIdRef.PeeledTag(Ref.Storage.LOOSE, name, ID_A, ID_B);
		assertTrue("is peeled", r.isPeeled());
		assertSame(ID_B, r.getPeeledObjectId());
	}

	public void testToString() {
		ObjectIdRef r;

		r = new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE, name, ID_A);
		assertEquals("Ref[" + name + "=" + ID_A.name() + "]", r.toString());
	}
}
