Ext.ns("EMobility.Intuity");

EMobility.Intuity.RFIDCards = Ext.extend(Ext.Panel, {
	initId: function() {
		return this._ID = {
			GRID	: Ext.id(),
			
			// Buttons
			ADD		: Ext.id(),
			SAVE	: Ext.id(),
			REFRESH	: Ext.id()
		};
	},
	
	initView: function() {
		this.getCards();
	},
	
	resetView: function() {
		// Reset form
		this.disableButtons(false);
	},
	
	store: new Ext.data.JsonStore({
			autoLoad : false,
			fields : ["cardType", "cardNumber", "created", "description"]				
		}),
	
	getCards: function () {
		var	view = this;
		
		this.showLoadingMessage("<@i18nText key='dp.profile.subscription.cards.loadingmsg' />");
		
		RFIDCardService.getRegisteredCards({
			callback: function(data){
				view.store.loadData(data);
				
				view.disableButtons(false);
				this.isGridDirty = false;
				
                // EMSG-353 - No Success Message
                this._view.hideMessage();
			},
			scope: view,
			exceptionScope: view
		});
	},
	
	viewMsg: "<@i18nText key='dp.profile.subscription.cards.viewmsg' />",
	
	isDirty: function() {
		var ds = this.store;
		if (this.isGridDirty || (ds && ds.getModifiedRecords().length > 0)) {
			return true;
		}
		return false;
	},
	
	disableButtons : function (disabled) {
		var E = Ext.getCmp,
			ID = this._ID;
			
		E(ID.SAVE).setDisabled(disabled);
		E(ID.REFRESH).setDisabled(disabled);
		E(ID.ADD).setDisabled(disabled);
	},	
		
	getGrid: function() {
		return this.findById(this._ID.GRID);
	},
	
	refreshRFIDCard: function() {
		var grid  = this.getGrid(),
			store = this.store;
		
		grid.stopEditing();
		store.rejectChanges();		
		
		this.showLoadingMessage("<@i18nText key='dp.profile.subscription.cards.loadingmsg' />");
		
		// Get updated cards
		this.getCards();
	},

	removeRFIDCard: function() {
		var view   = this,
			grid   = this.getGrid(),
			store  = this.store,
			record = grid.getSelectionModel().getSelected();

		if (record) {
			store.remove(record);
			this.isGridDirty = true;
		}
	},

	saveRFIDCards: function (cards) {
		var view   = this,
			grid = this.getGrid(),
			store = this.store;
		
		// Disable buttons 
		this.disableButtons(true);
			
		grid.stopEditing();
		
		var modifiedCards = [];
		Ext.each(store.getRange(), function(item, index, allitems){
			modifiedCards.push(item.data);
		});
		
		// In case of no modified records , serves as refresh ;)
		view.showLoadingMessage("<@i18nText key='dp.profile.subscription.cards.updatemsg' />");
		
		RFIDCardService.registerCards(modifiedCards, {
			callback: function(data){
				store.rejectChanges();
				store.loadData(data);
				
				view.disableButtons(false);
				this.isGridDirty = false;
				
				// Success message
				view.showSuccessMessage("<@i18nText key='dp.profile.subscription.cards.successmsg.submit' />");				
			},
			exceptionScope: view
		});
	},
	
	initComponent: function() {
		
		var view = this,
			p = this._view,
			ID = this.initId();
		
			// @isGridDirty
			view.isGridDirty = false;
		
		Ext.apply(this, {
			showLoadingMessage	: p.showLoadingMessage.createDelegate(p),
			showSuccessMessage	: p.showSuccessMessage.createDelegate(p),
			showInfoMessage		: p.showInfoMessage.createDelegate(p),
			showErrorMessage	: p.showErrorMessage.createDelegate(p),
			layout: 'fit',			
			items: {
				xtype: 'editorgrid',
				id: ID.GRID,
				style: 'padding-bottom: 10px',
				autoScroll: true,							    	
				viewConfig : {
					forceFit: true,
					headersDisabled: true,
					emptyText : "<@i18nText key='profile.subscription.cards.grid.emptytext' />"
				},
				sm : new Ext.grid.RowSelectionModel({ singleSelect : true }),
				store : this.store,
				
				trackMouseOver: false,
				enableHdMenu: false,
				enableColumnResize: false,
				enableColumnMove: false,
				cm : new Ext.grid.ColumnModel({
					defaults: { sortable: false },
					columns : [
						new Ext.ux.grid.RowNumberer(), 
						{
							header: "<@i18nText key='profile.subscription.cards.grid.cardnumber.header' />",
							width: 130,
							fixed: true,
							dataIndex : "cardNumber",
							editor : {
								xtype: 'textfield',
								allowBlank: false,
								selectOnFocus: true,
								maxLength: 16,
								minLength: 16,
								blankText: "<@i18nText key='profile.subscription.cards.grid.cardnumber.blanktext' />",
								minLengthText: "<@i18nText key='profile.subscription.cards.grid.cardnumber.minlentext' />",
								maxLengthText: "<@i18nText key='profile.subscription.cards.grid.cardnumber.minlentext' />",
								maskRe: /[0-9]/
							}
						}, 
						{
							header: "<@i18nText key='profile.subscription.cards.grid.description.header' />",
							width: 150,
							dataIndex:"description",
							editor : {
								xtype: 'textfield'
							}
						}, 
						{
							header: "<@i18nText key='profile.subscription.cards.grid.created.header' />",
							xtype: "datecolumn",
							width: 120,
							align: 'center',
							fixed: true,
							dataIndex : "created",		
							format: "d/m/Y"
						},
						{
							header : "<@i18nText key='profile.subscription.cards.grid.remove.header' />",
							width : 60,
							fixed: true,
							align: 'center',
							tooltip : "<@i18nText key='profile.subscription.cards.grid.remove.tooltip' />",
							renderer : function(v) {
								return "<div class='icon-x16-clear iarc-profile-rfid-btn-delete' title='Click to remove'></div>"; 
							},
							listeners:{
								click: this.removeRFIDCard.createDelegate(this)
							}									
						}
					]
				})
			},
			buttons: [
				{
					text : "<@i18nText key='common.button.add' />",
					id : ID.ADD,
					scope : this,
					handler : function(button, event) {
						var grid   = this.getGrid(),
							store  = this.store,								
							RFIDCard = store.recordType,									
							p = new RFIDCard({
								cardNumber		:  "",
								description		: "",
								created 		: new Date()
							});
							
						p.markDirty();
						grid.stopEditing();
						store.insert(0, p);
						store.getAt(0);
						grid.startEditing(0, 1);
						this.isGridDirty = true;
					}
				},
				{
					id : ID.SAVE,
					text : "<@i18nText key='common.button.save' />",
					scope : this,
					handler : this.saveRFIDCards.createDelegate(this)					
				}, 
				{
					id : ID.REFRESH,
					text : "<@i18nText key='common.button.refresh' />",					
					scope : this,
					handler : this.refreshRFIDCard.createDelegate(this)					
				}
			]
		});		
		
		EMobility.Intuity.RFIDCards.superclass.initComponent.call(this);
	}
});

Ext.reg('profile-subscription-rfid-cards', EMobility.Intuity.RFIDCards);