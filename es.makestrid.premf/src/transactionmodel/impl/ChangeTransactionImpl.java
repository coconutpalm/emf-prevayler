/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package transactionmodel.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.change.ChangeDescription;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import transactionmodel.ChangeTransaction;
import transactionmodel.TransactionType;
import transactionmodel.TransactionmodelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Change Transaction</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link transactionmodel.impl.ChangeTransactionImpl#getType <em>Type</em>}</li>
 *   <li>{@link transactionmodel.impl.ChangeTransactionImpl#getChange <em>Change</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ChangeTransactionImpl extends EObjectImpl implements ChangeTransaction {
	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final TransactionType TYPE_EDEFAULT = TransactionType.SURE_TRANSACTION_WITH_QUERY;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected TransactionType type = TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getChange() <em>Change</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChange()
	 * @generated
	 * @ordered
	 */
	protected static final ChangeDescription CHANGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getChange() <em>Change</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChange()
	 * @generated
	 * @ordered
	 */
	protected ChangeDescription change = CHANGE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ChangeTransactionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TransactionmodelPackage.Literals.CHANGE_TRANSACTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransactionType getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(TransactionType newType) {
		TransactionType oldType = type;
		type = newType == null ? TYPE_EDEFAULT : newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TransactionmodelPackage.CHANGE_TRANSACTION__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChangeDescription getChange() {
		return change;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setChange(ChangeDescription newChange) {
		ChangeDescription oldChange = change;
		change = newChange;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TransactionmodelPackage.CHANGE_TRANSACTION__CHANGE, oldChange, change));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TransactionmodelPackage.CHANGE_TRANSACTION__TYPE:
				return getType();
			case TransactionmodelPackage.CHANGE_TRANSACTION__CHANGE:
				return getChange();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TransactionmodelPackage.CHANGE_TRANSACTION__TYPE:
				setType((TransactionType)newValue);
				return;
			case TransactionmodelPackage.CHANGE_TRANSACTION__CHANGE:
				setChange((ChangeDescription)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case TransactionmodelPackage.CHANGE_TRANSACTION__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case TransactionmodelPackage.CHANGE_TRANSACTION__CHANGE:
				setChange(CHANGE_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case TransactionmodelPackage.CHANGE_TRANSACTION__TYPE:
				return type != TYPE_EDEFAULT;
			case TransactionmodelPackage.CHANGE_TRANSACTION__CHANGE:
				return CHANGE_EDEFAULT == null ? change != null : !CHANGE_EDEFAULT.equals(change);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (type: ");
		result.append(type);
		result.append(", change: ");
		result.append(change);
		result.append(')');
		return result.toString();
	}

} //ChangeTransactionImpl
