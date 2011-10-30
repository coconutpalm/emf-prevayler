/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package transactionmodel;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.change.ChangeDescription;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Change Transaction</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link transactionmodel.ChangeTransaction#getType <em>Type</em>}</li>
 *   <li>{@link transactionmodel.ChangeTransaction#getChange <em>Change</em>}</li>
 * </ul>
 * </p>
 *
 * @see transactionmodel.TransactionmodelPackage#getChangeTransaction()
 * @model
 * @generated
 */
public interface ChangeTransaction extends EObject {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link transactionmodel.TransactionType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see transactionmodel.TransactionType
	 * @see #setType(TransactionType)
	 * @see transactionmodel.TransactionmodelPackage#getChangeTransaction_Type()
	 * @model
	 * @generated
	 */
	TransactionType getType();

	/**
	 * Sets the value of the '{@link transactionmodel.ChangeTransaction#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see transactionmodel.TransactionType
	 * @see #getType()
	 * @generated
	 */
	void setType(TransactionType value);

	/**
	 * Returns the value of the '<em><b>Change</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Change</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Change</em>' attribute.
	 * @see #setChange(ChangeDescription)
	 * @see transactionmodel.TransactionmodelPackage#getChangeTransaction_Change()
	 * @model dataType="transactionmodel.ChangeDescription"
	 * @generated
	 */
	ChangeDescription getChange();

	/**
	 * Sets the value of the '{@link transactionmodel.ChangeTransaction#getChange <em>Change</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Change</em>' attribute.
	 * @see #getChange()
	 * @generated
	 */
	void setChange(ChangeDescription value);

} // ChangeTransaction
