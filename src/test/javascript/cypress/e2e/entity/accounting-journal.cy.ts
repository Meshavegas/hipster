import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('AccountingJournal e2e test', () => {
  const accountingJournalPageUrl = '/accounting-journal';
  const accountingJournalPageUrlPattern = new RegExp('/accounting-journal(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const accountingJournalSample = {};

  let accountingJournal;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/accounting-journals+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/accounting-journals').as('postEntityRequest');
    cy.intercept('DELETE', '/api/accounting-journals/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (accountingJournal) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/accounting-journals/${accountingJournal.id}`,
      }).then(() => {
        accountingJournal = undefined;
      });
    }
  });

  it('AccountingJournals menu should load AccountingJournals page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('accounting-journal');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('AccountingJournal').should('exist');
    cy.url().should('match', accountingJournalPageUrlPattern);
  });

  describe('AccountingJournal page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(accountingJournalPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create AccountingJournal page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/accounting-journal/new$'));
        cy.getEntityCreateUpdateHeading('AccountingJournal');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', accountingJournalPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/accounting-journals',
          body: accountingJournalSample,
        }).then(({ body }) => {
          accountingJournal = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/accounting-journals+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/accounting-journals?page=0&size=20>; rel="last",<http://localhost/api/accounting-journals?page=0&size=20>; rel="first"',
              },
              body: [accountingJournal],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(accountingJournalPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details AccountingJournal page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('accountingJournal');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', accountingJournalPageUrlPattern);
      });

      it('edit button click should load edit AccountingJournal page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('AccountingJournal');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', accountingJournalPageUrlPattern);
      });

      it('edit button click should load edit AccountingJournal page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('AccountingJournal');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', accountingJournalPageUrlPattern);
      });

      it('last delete button click should delete instance of AccountingJournal', () => {
        cy.intercept('GET', '/api/accounting-journals/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('accountingJournal').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', accountingJournalPageUrlPattern);

        accountingJournal = undefined;
      });
    });
  });

  describe('new AccountingJournal page', () => {
    beforeEach(() => {
      cy.visit(`${accountingJournalPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('AccountingJournal');
    });

    it('should create an instance of AccountingJournal', () => {
      cy.get(`[data-cy="direction"]`).select('CREDIT');

      cy.get(`[data-cy="amount"]`).type('31067').should('have.value', '31067');

      cy.get(`[data-cy="balanceBefore"]`).type('71696').should('have.value', '71696');

      cy.get(`[data-cy="balanceAfter"]`).type('49360').should('have.value', '49360');

      cy.get(`[data-cy="createAt"]`).type('2023-03-15T03:17').blur().should('have.value', '2023-03-15T03:17');

      cy.get(`[data-cy="updateAt"]`).type('2023-03-14T19:14').blur().should('have.value', '2023-03-14T19:14');

      cy.get(`[data-cy="createBy"]`).type('lavender').should('have.value', 'lavender');

      cy.get(`[data-cy="updateBy"]`).type('auxiliary violet reboot').should('have.value', 'auxiliary violet reboot');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        accountingJournal = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', accountingJournalPageUrlPattern);
    });
  });
});
