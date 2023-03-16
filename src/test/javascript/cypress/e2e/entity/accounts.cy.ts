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

describe('Accounts e2e test', () => {
  const accountsPageUrl = '/accounts';
  const accountsPageUrlPattern = new RegExp('/accounts(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const accountsSample = {};

  let accounts;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/accounts+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/accounts').as('postEntityRequest');
    cy.intercept('DELETE', '/api/accounts/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (accounts) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/accounts/${accounts.id}`,
      }).then(() => {
        accounts = undefined;
      });
    }
  });

  it('Accounts menu should load Accounts page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('accounts');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Accounts').should('exist');
    cy.url().should('match', accountsPageUrlPattern);
  });

  describe('Accounts page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(accountsPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Accounts page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/accounts/new$'));
        cy.getEntityCreateUpdateHeading('Accounts');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', accountsPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/accounts',
          body: accountsSample,
        }).then(({ body }) => {
          accounts = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/accounts+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/accounts?page=0&size=20>; rel="last",<http://localhost/api/accounts?page=0&size=20>; rel="first"',
              },
              body: [accounts],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(accountsPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Accounts page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('accounts');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', accountsPageUrlPattern);
      });

      it('edit button click should load edit Accounts page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Accounts');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', accountsPageUrlPattern);
      });

      it('edit button click should load edit Accounts page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Accounts');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', accountsPageUrlPattern);
      });

      it('last delete button click should delete instance of Accounts', () => {
        cy.intercept('GET', '/api/accounts/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('accounts').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', accountsPageUrlPattern);

        accounts = undefined;
      });
    });
  });

  describe('new Accounts page', () => {
    beforeEach(() => {
      cy.visit(`${accountsPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Accounts');
    });

    it('should create an instance of Accounts', () => {
      cy.get(`[data-cy="accountNumber"]`).type('instruction').should('have.value', 'instruction');

      cy.get(`[data-cy="balance"]`).type('34038').should('have.value', '34038');

      cy.get(`[data-cy="currency"]`).type('b override a').should('have.value', 'b override a');

      cy.get(`[data-cy="createAt"]`).type('2023-03-15T01:11').blur().should('have.value', '2023-03-15T01:11');

      cy.get(`[data-cy="updateAt"]`).type('2023-03-15T03:12').blur().should('have.value', '2023-03-15T03:12');

      cy.get(`[data-cy="createBy"]`).type('Bac').should('have.value', 'Bac');

      cy.get(`[data-cy="updateBy"]`).type('Home sensor').should('have.value', 'Home sensor');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        accounts = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', accountsPageUrlPattern);
    });
  });
});
