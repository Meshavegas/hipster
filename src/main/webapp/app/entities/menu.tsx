import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/users">
        <Translate contentKey="global.menu.entities.users" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/accounts">
        <Translate contentKey="global.menu.entities.accounts" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/accounting-journal">
        <Translate contentKey="global.menu.entities.accountingJournal" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
