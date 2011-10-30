/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package transactionmodel.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.change.ChangeDescription;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import transactionmodel.ChangeTransaction;
import transactionmodel.TransactionType;
import transactionmodel.TransactionmodelFactory;
import transactionmodel.TransactionmodelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TransactionmodelPackageImpl extends EPackageImpl implements TransactionmodelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass changeTransactionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum transactionTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType changeDescriptionEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see transactionmodel.TransactionmodelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private TransactionmodelPackageImpl() {
		super(eNS_URI, TransactionmodelFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link TransactionmodelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static TransactionmodelPackage init() {
		if (isInited) return (TransactionmodelPackage)EPackage.Registry.INSTANCE.getEPackage(TransactionmodelPackage.eNS_URI);

		// Obtain or create and register package
		TransactionmodelPackageImpl theTransactionmodelPackage = (TransactionmodelPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof TransactionmodelPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new TransactionmodelPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theTransactionmodelPackage.createPackageContents();

		// Initialize created meta-data
		theTransactionmodelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theTransactionmodelPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(TransactionmodelPackage.eNS_URI, theTransactionmodelPackage);
		return theTransactionmodelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getChangeTransaction() {
		return changeTransactionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getChangeTransaction_Type() {
		return (EAttribute)changeTransactionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getChangeTransaction_Change() {
		return (EAttribute)changeTransactionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getTransactionType() {
		return transactionTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getChangeDescription() {
		return changeDescriptionEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransactionmodelFactory getTransactionmodelFactory() {
		return (TransactionmodelFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		changeTransactionEClass = createEClass(CHANGE_TRANSACTION);
		createEAttribute(changeTransactionEClass, CHANGE_TRANSACTION__TYPE);
		createEAttribute(changeTransactionEClass, CHANGE_TRANSACTION__CHANGE);

		// Create enums
		transactionTypeEEnum = createEEnum(TRANSACTION_TYPE);

		// Create data types
		changeDescriptionEDataType = createEDataType(CHANGE_DESCRIPTION);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(changeTransactionEClass, ChangeTransaction.class, "ChangeTransaction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getChangeTransaction_Type(), this.getTransactionType(), "type", null, 0, 1, ChangeTransaction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getChangeTransaction_Change(), this.getChangeDescription(), "change", null, 0, 1, ChangeTransaction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(transactionTypeEEnum, TransactionType.class, "TransactionType");
		addEEnumLiteral(transactionTypeEEnum, TransactionType.SURE_TRANSACTION_WITH_QUERY);
		addEEnumLiteral(transactionTypeEEnum, TransactionType.TRANSACTION);
		addEEnumLiteral(transactionTypeEEnum, TransactionType.TRANSACTION_WITH_QUERY);

		// Initialize data types
		initEDataType(changeDescriptionEDataType, ChangeDescription.class, "ChangeDescription", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //TransactionmodelPackageImpl
