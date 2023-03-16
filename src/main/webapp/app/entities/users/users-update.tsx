import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUsers } from 'app/shared/model/users.model';
import { getEntity, updateEntity, createEntity, reset } from './users.reducer';

export const UsersUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const usersEntity = useAppSelector(state => state.users.entity);
  const loading = useAppSelector(state => state.users.loading);
  const updating = useAppSelector(state => state.users.updating);
  const updateSuccess = useAppSelector(state => state.users.updateSuccess);

  const handleClose = () => {
    navigate('/users' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.createAt = convertDateTimeToServer(values.createAt);
    values.updateAt = convertDateTimeToServer(values.updateAt);

    const entity = {
      ...usersEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          createAt: displayDefaultDateTime(),
          updateAt: displayDefaultDateTime(),
        }
      : {
          ...usersEntity,
          createAt: convertDateTimeFromServer(usersEntity.createAt),
          updateAt: convertDateTimeFromServer(usersEntity.updateAt),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="myApp.users.home.createOrEditLabel" data-cy="UsersCreateUpdateHeading">
            <Translate contentKey="myApp.users.home.createOrEditLabel">Create or edit a Users</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="users-id"
                  label={translate('myApp.users.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('myApp.users.name')} id="users-name" name="name" data-cy="name" type="text" />
              <ValidatedField label={translate('myApp.users.email')} id="users-email" name="email" data-cy="email" type="text" />
              <ValidatedField
                label={translate('myApp.users.password')}
                id="users-password"
                name="password"
                data-cy="password"
                type="text"
              />
              <ValidatedField
                label={translate('myApp.users.createAt')}
                id="users-createAt"
                name="createAt"
                data-cy="createAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('myApp.users.updateAt')}
                id="users-updateAt"
                name="updateAt"
                data-cy="updateAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('myApp.users.createBy')}
                id="users-createBy"
                name="createBy"
                data-cy="createBy"
                type="text"
              />
              <ValidatedField
                label={translate('myApp.users.updateBy')}
                id="users-updateBy"
                name="updateBy"
                data-cy="updateBy"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/users" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default UsersUpdate;
