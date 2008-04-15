/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: M:\\projects\\Castor\\src\\com\\eldergods\\Castor\\Service\\ICastorStreamBrowserService.aidl
 */
package com.eldergods.Castor.Service;
import java.lang.String;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Binder;
import android.os.Parcel;
public interface ICastorStreamBrowserService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.eldergods.Castor.Service.ICastorStreamBrowserService
{
private static final java.lang.String DESCRIPTOR = "com.eldergods.Castor.Service.ICastorStreamBrowserService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an ICastorStreamBrowserService interface,
 * generating a proxy if needed.
 */
public static com.eldergods.Castor.Service.ICastorStreamBrowserService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
com.eldergods.Castor.Service.ICastorStreamBrowserService in = (com.eldergods.Castor.Service.ICastorStreamBrowserService)obj.queryLocalInterface(DESCRIPTOR);
if ((in!=null)) {
return in;
}
return new com.eldergods.Castor.Service.ICastorStreamBrowserService.Stub.Proxy(obj);
}
public android.os.IBinder asBinder()
{
return this;
}
public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags)
{
try {
switch (code)
{
case TRANSACTION_getGenres:
{
java.lang.String[] _result = this.getGenres();
reply.writeStringArray(_result);
return true;
}
case TRANSACTION_getStations:
{
java.lang.String _arg0;
_arg0 = data.readString();
com.eldergods.Castor.Service.ICastorStation _result = this.getStations(_arg0);
reply.writeStrongBinder((((_result!=null))?(_result.asBinder()):(null)));
return true;
}
}
}
catch (android.os.DeadObjectException e) {
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.eldergods.Castor.Service.ICastorStreamBrowserService
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String[] getGenres() throws android.os.DeadObjectException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String[] _result;
try {
mRemote.transact(Stub.TRANSACTION_getGenres, _data, _reply, 0);
_result = _reply.createStringArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public com.eldergods.Castor.Service.ICastorStation getStations(java.lang.String genre) throws android.os.DeadObjectException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.eldergods.Castor.Service.ICastorStation _result;
try {
_data.writeString(genre);
mRemote.transact(Stub.TRANSACTION_getStations, _data, _reply, 0);
_result = com.eldergods.Castor.Service.ICastorStation.Stub.asInterface(_reply.readStrongBinder());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_getGenres = (IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_getStations = (IBinder.FIRST_CALL_TRANSACTION + 1);
}
public java.lang.String[] getGenres() throws android.os.DeadObjectException;
public com.eldergods.Castor.Service.ICastorStation getStations(java.lang.String genre) throws android.os.DeadObjectException;
}
